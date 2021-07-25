/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Bot.kt] created by Ji Sungbin on 21. 7. 10. 오전 11:20.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.bot

import android.app.Notification
import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import io.github.jisungbin.gitmessengerbot.activity.main.script.ScriptItem
import io.github.jisungbin.gitmessengerbot.activity.main.script.getScriptDefaultCode
import io.github.jisungbin.gitmessengerbot.activity.main.setting.model.App
import io.github.jisungbin.gitmessengerbot.activity.main.setting.model.MutableApp
import io.github.jisungbin.gitmessengerbot.bot.debug.DebugStore
import io.github.jisungbin.gitmessengerbot.bot.debug.Sender
import io.github.jisungbin.gitmessengerbot.bot.debug.createDebugItem
import io.github.jisungbin.gitmessengerbot.util.Json
import io.github.jisungbin.gitmessengerbot.util.Storage
import io.github.jisungbin.gitmessengerbot.util.Util
import io.github.jisungbin.gitmessengerbot.util.config.StringConfig

@Suppress("ObjectPropertyName")
object Bot {
    private val _app = mutableStateOf(MutableApp())
    private val _scripts = SnapshotStateList<ScriptItem>()
    private val _scriptPowers: HashMap<Int, MutableState<Boolean>> = hashMapOf()
    private val _compileStates: HashMap<Int, MutableState<Boolean>> = hashMapOf()

    val scripts
        get() = _scripts
            .sortedByDescending { it.name }
            .sortedByDescending { it.lang }
            .asReversed()
    val app: State<MutableApp> get() = _app

    init {
        _scripts.addAll(getList())
        Storage.read(StringConfig.AppData, null)?.let { appDataJson ->
            @Suppress("LocalVariableName")
            val _app = Json.toModel(appDataJson, App::class)
            val app = MutableApp(
                power = mutableStateOf(_app.power),
                evalMode = mutableStateOf(_app.evalMode),
                editorFontSize = mutableStateOf(_app.editorFontSize),
                editorAutoSave = mutableStateOf(_app.editorAutoSave),
                scriptDefaultCode = mutableStateOf(_app.scriptDefaultCode),
                scriptDefaultLang = mutableStateOf(_app.scriptDefaultLang),
                scriptResponseFunctionName = mutableStateOf(_app.scriptResponseFunctionName),
                gitDefaultBranch = mutableStateOf(_app.gitDefaultBranch),
                gitDefaultCommitMessage = mutableStateOf(_app.gitDefaultCommitMessage),
                gitDefaultRepoOptions = mutableStateOf(_app.gitDefaultRepoOptions),
                kakaoTalkPackageNames = mutableStateListOf<String>().apply {
                    addAll(_app.kakaoTalkPackageNames)
                },
                useHorizontalScroll = mutableStateOf(_app.useHorizontalScroll)
            )
            this._app.value = app
        }
    }

    fun getPowerOnScripts() = scripts.filter { it.power }

    fun getCompiledScripts() = scripts.filter { it.power && it.compiled }

    /**
     * 앱 정보 json 파일 저장
     */
    fun saveAndUpdate(app: MutableApp) {
        _app.value = app
        @Suppress("LocalVariableName")
        val _app = App(
            power = app.power.value,
            evalMode = app.evalMode.value,
            editorFontSize = app.editorFontSize.value,
            editorAutoSave = app.editorAutoSave.value,
            scriptDefaultCode = app.scriptDefaultCode.value,
            scriptDefaultLang = app.scriptDefaultLang.value,
            scriptResponseFunctionName = app.scriptResponseFunctionName.value,
            gitDefaultBranch = app.gitDefaultBranch.value,
            gitDefaultCommitMessage = app.gitDefaultCommitMessage.value,
            gitDefaultRepoOptions = app.gitDefaultRepoOptions.value,
            kakaoTalkPackageNames = app.kakaoTalkPackageNames.toList(),
            useHorizontalScroll = app.useHorizontalScroll.value
        )
        Storage.write(StringConfig.AppData, Json.toString(_app))
    }

    /**
     * 스크립트 추가 및 json 정보 파일 저장
     */
    fun saveAndUpdate(script: ScriptItem) {
        _scripts.removeIf { it.id == script.id }
        _scripts.add(script)
        Storage.write(StringConfig.ScriptData(script.name, script.lang), Json.toString(script))
    }

    /**
     * 스크립트 소스코드 저장
     */
    fun saveAndUpdate(script: ScriptItem, code: String) {
        Storage.write(
            StringConfig.Script(script.name, script.lang),
            code
        )
    }

    fun addScript(script: ScriptItem) {
        _scripts.add(script)
        Storage.write(
            StringConfig.Script(script.name, script.lang),
            script.lang.getScriptDefaultCode()
        )
        Storage.write(StringConfig.ScriptData(script.name, script.lang), Json.toString(script))
    }

    fun removeScript(script: ScriptItem) {
        _scripts.remove(script)
        Storage.delete(StringConfig.Script(script.name, script.lang))
    }

    private fun getList(): List<ScriptItem> {
        val scripts = mutableListOf<ScriptItem>()
        repeat(4) { lang -> // 스크립트 코드파일이 아니라, 스크립트 정보 파일을 읽어와야함
            Storage.fileList(StringConfig.ScriptPath(lang)).filter { it.path.endsWith(".json") }
                .forEach { scriptDataFile ->
                    val script =
                        Json.toModel(Storage.read(scriptDataFile.path, null)!!, ScriptItem::class)
                    if (script.compiled) {
                        if (StackManager.v8[script.id] == null) {
                            script.compiled = false
                        }
                    }
                    scripts.add(script)
                }
        }
        return scripts
    }

    fun getScriptById(id: Int) = scripts.first { it.id == id }

    fun getCode(script: ScriptItem) =
        Storage.read(StringConfig.Script(script.name, script.lang), "")!!

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
            Util.error(context, "메시지 답장 실패\n\n$exception")
        }
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
            val v8 = StackManager.v8[script.id] ?: run {
                println("${script.name} - v8 instance is null")
                return
            }
            v8.locker.acquire()
            if (script.id == StringConfig.ScriptEvalId) {
                val result = v8.executeScript(message).toString()
                DebugStore.add(
                    createDebugItem(
                        StringConfig.ScriptEvalId,
                        result,
                        "null",
                        Sender.Bot
                    )
                )
            } else {
                val arguments =
                    listOf(room, message, sender, isGroupChat, "null", isDebugMode) // todo
                v8.executeJSFunction(
                    app.value.scriptResponseFunctionName.value,
                    *arguments.toTypedArray()
                )
            }
            v8.locker.release()
            println("${script.name}: 실행됨")
        } catch (exception: Exception) {
            Util.error(context, "js response 호출 실패\n\n${exception.message}")
        }
    }
}
