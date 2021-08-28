/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Bot.kt] created by Ji Sungbin on 21. 8. 28. 오후 4:52
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.bot

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.jisungbin.gitmessengerbot.util.config.ScriptConfig
import io.github.jisungbin.gitmessengerbot.util.core.Storage
import io.github.jisungbin.gitmessengerbot.util.exception.CoreException
import io.github.jisungbin.gitmessengerbot.util.extension.edit
import io.github.jisungbin.gitmessengerbot.util.extension.toJsonString
import io.github.jisungbin.gitmessengerbot.util.extension.toModel
import io.github.jisungbin.gitmessengerbot.util.operator.minusAssign
import io.github.jisungbin.gitmessengerbot.util.operator.plusAssign
import io.github.sungbin.gitmessengerbot.core.bot.debug.DebugStore
import io.github.sungbin.gitmessengerbot.core.bot.debug.createDebugItem
import io.github.sungbin.gitmessengerbot.core.bot.script.ScriptItem

object Sender {
    const val Bot = "Bot"
}

object Bot {
    private val _scripts = MutableLiveData(getList())
    private val scriptPowers: HashMap<Int, MutableLiveData<Boolean>> = hashMapOf()
    private val compileStates: HashMap<Int, MutableLiveData<Boolean>> = hashMapOf()

    val scripts get(): LiveData<List<ScriptItem>> = _scripts

    /**
     * 스크립트 추가 및 json 정보 파일 저장
     */
    fun scriptDataSaveAndUpdate(script: ScriptItem) {
        _scripts.edit {
            removeIf { it.id == script.id }
            add(script)
        }
        Storage.write(ScriptConfig.ScriptDataPath(script.name, script.lang), script.toJsonString())
    }

    /**
     * 스크립트 소스코드 저장
     */
    fun scriptCodeSave(script: ScriptItem, code: String) {
        Storage.write(ScriptConfig.ScriptPath(script.name, script.lang), code)
    }

    fun addScript(script: ScriptItem) {
        _scripts += script
    }

    fun removeScript(script: ScriptItem) {
        _scripts -= script
        script.delete()
    }

    private fun getList(): List<ScriptItem> {
        val scripts = mutableListOf<ScriptItem>()
        repeat(4) { lang -> // 스크립트 코드파일이 아니라, 스크립트 정보 파일을 읽어와야함
            Storage.fileList(ScriptConfig.ScriptListPath(lang)).filter { it.path.endsWith(".json") }
                .forEach { scriptDataFile ->
                    val script: ScriptItem = Storage.read(scriptDataFile.path, null)!!.toModel()
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

    fun replyToSession(context: Context, session: NotificationCompat.Action, message: String) {
        try {
            val sendIntent = Intent()
            val messageBundle = Bundle()
            for (
                inputable in session.remoteInputs ?: throw CoreException("remoteInputs cannot be null.")
            ) {
                messageBundle.putCharSequence(inputable.resultKey, message)
            }
            RemoteInput.addResultsToIntent(session.remoteInputs, sendIntent, messageBundle)
            session.actionIntent.send(context, 0, sendIntent)
        } catch (exception: Exception) {
            throw CoreException(exception.message)
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
            if (script.id == ScriptConfig.EvalId) {
                val result = v8.executeScript(message).toString()
                DebugStore.add(
                    createDebugItem(
                        ScriptConfig.EvalId,
                        result,
                        "null",
                        Sender.Bot
                    )
                )
            } else {
                val arguments =
                    listOf(room, message, sender, isGroupChat, "null", isDebugMode) // todo
                v8.executeJSFunction(
                    ScriptConfig.DefaultResponseFunctionName,
                    *arguments.toTypedArray()
                )
            }
            v8.locker.release()
            println("${script.name}: 실행됨")
        } catch (exception: Exception) {
            throw CoreException(exception.message)
        }
    }
}
