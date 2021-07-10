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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import me.sungbin.gitmessengerbot.App
import me.sungbin.gitmessengerbot.activity.main.script.ScriptClass
import me.sungbin.gitmessengerbot.activity.main.script.ScriptItem
import me.sungbin.gitmessengerbot.bot.api.BotApi
import me.sungbin.gitmessengerbot.bot.api.Log
import me.sungbin.gitmessengerbot.util.Json
import me.sungbin.gitmessengerbot.util.Script
import me.sungbin.gitmessengerbot.util.Storage
import me.sungbin.gitmessengerbot.util.Util
import me.sungbin.gitmessengerbot.util.config.PathConfig

@Suppress("ObjectPropertyName")
object Bot {
    private val _app = mutableStateOf(App())
    private val _scripts = SnapshotStateList<ScriptItem>()
    private val _scriptPowers: HashMap<Int, MutableState<Boolean>> = hashMapOf()
    private val _compileStates: HashMap<Int, MutableState<Boolean>> = hashMapOf()

    val scripts
        get() = _scripts
            .sortedByDescending { it.name }
            .sortedByDescending { it.lang }
            .asReversed()
    val app: State<App> get() = _app // flow; unnecessary gatter

    init {
        _scripts.addAll(Script.getList())
        Storage.read(PathConfig.AppData, null)?.let { appDataJson ->
            _app.value = Json.toModel(appDataJson, App::class)
        }
    }

    fun getPowerOnScripts() = scripts.filter { it.power }

    /**
     * 앱 정보 json 파일 저장
     */
    fun save(app: App) {
        _app.value = app
        Storage.write(PathConfig.AppData, Json.toString(app))
    }

    /**
     * 스크립트 추가 및 json 정보 파일 저장
     */
    fun save(script: ScriptItem) {
        _scripts.removeIf { it.id == script.id }
        _scripts.add(script)
        Storage.write(PathConfig.ScriptData(script.name, script.lang), Json.toString(script))
    }

    /**
     * 스크립트 소스코드 저장
     */
    fun save(script: ScriptItem, code: String) {
        Storage.write(
            PathConfig.Script(script.name, script.lang),
            code
        )
    }

    fun addScript(script: ScriptItem) {
        _scripts.add(script)
        Script.create(script)
    }

    fun removeScript(script: ScriptItem) {
        _scripts.remove(script)
        Script.remove(script)
    }

    fun getScriptById(id: Int) = scripts.first { it.id == id }

    fun getCode(script: ScriptItem) =
        Storage.read(PathConfig.Script(script.name, script.lang), "")!!

    fun getClassList(scriptItem: ScriptItem): List<ScriptClass> { // todo
        return List(100) { ScriptClass(name = "test{$it}", code = "") }
    }

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
            Util.error(context, exception)
        }
    }

    fun compileJavaScript(context: Context, script: ScriptItem, code: String) = try {
        val v8 = V8.createV8Runtime()
        v8.addApi(
            apiName = "Bot",
            apiClass = BotApi(context),
            methodNameArray = listOf("reply", "replyShowAll"),
            argumentsListArray = listOf(
                listOf(String::class.java, String::class.java),
                listOf(String::class.java, String::class.java, String::class.java)
            )
        )
        v8.addApi(
            apiName = "Log",
            apiClass = Log(),
            methodNameArray = listOf("print"),
            argumentsListArray = listOf(listOf(Any::class.java))
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
        v8.executeScript(code)
        StackManager.v8[script.id] = v8
        println("script compiled: ${script.id}")
        println("code: $code")
        v8.locker.release()
        CompileResult.Success
    } catch (exception: Exception) {
        exception.printStackTrace()
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
                val arguments = V8Object(v8).apply {
                    add("room", room)
                    add("message", message)
                    add("sender", sender)
                    add("isGroupChat", isGroupChat)
                    add("profileImageBase64", "null") // todo
                }
                v8.executeJSFunction("onMessage", arguments)
                v8.locker.release()
                println("${script.name}: 실행됨")
            } else {
                // todo: 디버그 만들기
            }
        } catch (exception: Exception) {
            Util.error(context, exception)
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
}
