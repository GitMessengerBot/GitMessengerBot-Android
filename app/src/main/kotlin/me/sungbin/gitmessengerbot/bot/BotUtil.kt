/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Bot.kt] created by Ji Sungbin on 21. 7. 10. 오전 11:20.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.bot

import android.app.Notification
import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import me.sungbin.gitmessengerbot.activity.main.script.ScriptItem
import me.sungbin.gitmessengerbot.bot.api.Bot
import me.sungbin.gitmessengerbot.util.Storage
import me.sungbin.gitmessengerbot.util.config.PathConfig
import me.sungbin.gitmessengerbot.util.extension.toast

object BotUtil {
    fun replyToSession(context: Context, session: Notification.Action, message: String) {
        try {
            val sendIntent = Intent()
            val msg = Bundle()
            for (inputable in session.remoteInputs) msg.putCharSequence(
                inputable.resultKey,
                message
            )
            RemoteInput.addResultsToIntent(session.remoteInputs, sendIntent, msg)
            session.actionIntent.send(context, 0, sendIntent)
        } catch (exception: Exception) {
            toast(context, "Error: replyToSession (${exception.message})")
            println(exception.message)
        }
    }

    fun compileJavaScript(context: Context, script: ScriptItem) = try {
        val v8 = V8.createV8Runtime()
        v8.addApi(
            apiName = "Bot",
            apiClass = Bot(context),
            methodNameArray = listOf("reply", "replyShowAll"),
            argumentsListArray = listOf(
                listOf(String::class.java, String::class.java),
                listOf(String::class.java, String::class.java, String::class.java)
            )
        )
        /*v8.addApi(
            "Api",
            Api(),
            arrayOf("runRhino"),
            arrayOf(
                arrayOf(String::class.java)
            )
        )
        v8.addApi(
            "File",
            File(),
            arrayOf("save", "read"),
            arrayOf(
                arrayOf(String::class.java, String::class.java),
                arrayOf(String::class.java, String::class.java)
            )
        )
        v8.addApi(
            "Image",
            Image(),
            arrayOf("getLastImage", "getProfileImage"),
            arrayOf(
                arrayOf(),
                arrayOf(String::class.java)
            )
        )
        v8.addApi(
            "Log",
            Log(),
            arrayOf("test", "e", "d", "i"),
            arrayOf(
                arrayOf(Any::class.java),
                arrayOf(String::class.java),
                arrayOf(String::class.java),
                arrayOf(String::class.java)
            )
        )
        v8.addApi(
            "UI",
            UI(),
            arrayOf("toast", "notification"),
            arrayOf(
                arrayOf(String::class.java),
                arrayOf(String::class.java, String::class.java, Int::class.java),
            )
        )*/
        v8.executeScript(loadCode(script))
        StackManager.v8[script.id] = v8
        v8.locker.release()
        CompileResult.Success
    } catch (exception: Exception) {
        CompileResult.Error(exception)
    }

    fun callJsResponder(
        context: Context,
        script: ScriptItem,
        room: String,
        message: String,
        sender: String,
        isGroupChat: Boolean,
        isDebugMode: Boolean
    ) {
        try {
            if (!isDebugMode) {
                val v8 = StackManager.v8[script.id] ?: run {
                    println("${script.name} - v8 instance is null")
                    return
                }
                v8.locker.acquire()
                val arguments = V8Object(v8).run {
                    add("room", room)
                    add("message", message)
                    add("sender", sender)
                    add("isGroupChat", isGroupChat)
                }
                v8.executeJSFunction("onMessage", arguments)
                v8.locker.release()
                println("${script.name}: 실행됨")
            } else {
                // todo: 디버그 만들기
            }
        } catch (exception: Exception) {
            toast(context, exception.message!!)
        }
    }

    @Suppress("DEPRECATION")
    private fun V8.addApi(
        apiName: String,
        apiClass: Any,
        methodNameArray: List<String>,
        argumentsListArray: List<List<Class<*>>>
    ) {
        val api = V8Object(this)
        this.add(apiName, api)

        for ((index, methodName) in methodNameArray.withIndex()) {
            api.registerJavaMethod(
                apiClass,
                methodName,
                methodName,
                argumentsListArray[index].toTypedArray()
            )
        }

        api.release()
    }

    private fun loadCode(script: ScriptItem) =
        Storage.read(PathConfig.Script(script.name, script.lang), "")!!
}
