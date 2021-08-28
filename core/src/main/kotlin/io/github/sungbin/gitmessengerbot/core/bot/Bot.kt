/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Bot.kt] created by Ji Sungbin on 21. 8. 28. 오후 4:52
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.bot

import android.app.Notification
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.jisungbin.gitmessengerbot.util.Storage
import io.github.jisungbin.gitmessengerbot.util.Util
import io.github.jisungbin.gitmessengerbot.util.config.ScriptConfig
import io.github.jisungbin.gitmessengerbot.util.config.StringConfig
import io.github.jisungbin.gitmessengerbot.util.extension.edit
import io.github.jisungbin.gitmessengerbot.util.extension.toJsonString
import io.github.sungbin.gitmessengerbot.core.bot.debug.DebugStore
import io.github.sungbin.gitmessengerbot.core.bot.debug.Sender
import io.github.sungbin.gitmessengerbot.core.bot.script.ScriptItem

object Bot {
    private val _scripts = MutableLiveData<List<ScriptItem>>().apply {
        value = getList()
    }
    private val _scriptPowers: HashMap<Int, LiveData<Boolean>> = hashMapOf()
    private val _compileStates: HashMap<Int, LiveData<Boolean>> = hashMapOf()

    val scripts get(): LiveData<List<ScriptItem>> = _scripts

    /**
     * 스크립트 추가 및 json 정보 파일 저장
     */
    fun saveAndUpdate(script: ScriptItem) {
        _scripts.edit {
            removeIf { it.id == script.id }
            add(script)
            this
        }
        Storage.write(ScriptConfig.ScriptDataPath(script.name, script.lang), script.toJsonString())
    }

    /**
     * 스크립트 소스코드 저장
     */
    fun saveAndUpdate(script: ScriptItem, code: String) {
        Storage.write(ScriptConfig.ScriptPath(script.name, script.lang), code)
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
        isDebugMode: Boolean,
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
