package com.sungbin.gitkakaobot.bot

import android.app.Notification
import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.sungbin.androidutils.util.Logger
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.bot.api.*
import com.sungbin.gitkakaobot.bot.api.Bot
import com.sungbin.gitkakaobot.model.BotCompile
import com.sungbin.gitkakaobot.util.BotUtil
import com.sungbin.gitkakaobot.util.UiUtil
import com.sungbin.gitkakaobot.util.manager.StackManager


/**
 * Created by SungBin on 2020-12-18.
 */

object Bot {

    private lateinit var context: Context
    fun init(context: Context) {
        this.context = context
    }

    fun replyToSession(session: Notification.Action?, value: String) {
        if (session == null) {
            UiUtil.toast(context, context.getString(R.string.bot_cant_load_session))
        } else {
            try {
                val sendIntent = Intent()
                val msg = Bundle()
                for (inputable in session.remoteInputs) msg.putCharSequence(
                    inputable.resultKey,
                    value
                )
                RemoteInput.addResultsToIntent(session.remoteInputs, sendIntent, msg)
                session.actionIntent.send(context, 0, sendIntent)
            } catch (exception: Exception) {
                UiUtil.error(context, exception)
            }
        }
    }

    fun compileJavaScript(bot: com.sungbin.gitkakaobot.model.Bot): BotCompile {
        return try {
            val v8 = V8.createV8Runtime()
            v8.addApi(
                "Api",
                Api(),
                arrayOf("runRhino"),
                arrayOf(
                    arrayOf(String::class.java)
                )
            )
            v8.addApi(
                "Bot",
                Bot(),
                arrayOf("reply", "replyShowAll"),
                arrayOf(
                    arrayOf(String::class.java, String::class.java),
                    arrayOf(String::class.java, String::class.java, String::class.java)
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
                arrayOf("toast", "notification", "snackbar"),
                arrayOf(
                    arrayOf(String::class.java),
                    arrayOf(String::class.java, String::class.java, Int::class.java),
                    arrayOf(View::class.java, String::class.java)
                )
            )
            v8.executeScript(BotUtil.getBotCode(bot))
            StackManager.v8[bot.uuid] = v8
            v8.locker.release()
            BotCompile(true, null)
        } catch (exception: Exception) {
            BotCompile(false, exception)
        }
    }

    fun callJsResponder(
        bot: com.sungbin.gitkakaobot.model.Bot,
        room: String,
        message: String,
        sender: String,
        isGroupChat: Boolean,
        packageName: String,
        isDebugMode: Boolean
    ) {
        try {
            val v8 = StackManager.v8[bot.uuid] ?: run {
                Logger.w("${bot.name} - v8 instance is null")
                return
            }
            v8.locker.acquire()
            val arguments = V8Object(v8).run {
                add("room", room)
                add("message", message)
                add("sender", sender)
                add("isGroupChat", isGroupChat)
                add("packageName", packageName)
            }
            v8.executeJSFunction("onMessage", arguments)
            v8.locker.release()
            Logger.w("${bot.name}: 실행됨")
        } catch (exception: Exception) {
            UiUtil.error(context, exception)
        }
    }

    private fun V8.addApi(
        apiName: String,
        apiClass: Any,
        methodNameArray: Array<String>,
        argumentsListArray: Array<Array<Class<*>>>
    ) {
        val api = V8Object(this)
        this.add(apiName, api)

        for ((index, methodName) in methodNameArray.withIndex()) {
            api.registerJavaMethod(
                apiClass,
                methodName,
                methodName,
                argumentsListArray[index]
            )
        }

        api.release() // todo: deprecated? 그럼 다른거 뭐 써야하는데!!
    }

}