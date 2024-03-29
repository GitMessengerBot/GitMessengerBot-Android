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
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import io.github.jisungbin.gitmessengerbot.common.constant.ScriptConstant
import io.github.jisungbin.gitmessengerbot.common.core.Storage
import io.github.jisungbin.gitmessengerbot.common.exception.CoreException
import io.github.jisungbin.gitmessengerbot.common.extension.edit
import io.github.jisungbin.gitmessengerbot.common.extension.toJsonString
import io.github.jisungbin.gitmessengerbot.common.extension.toModel
import io.github.jisungbin.gitmessengerbot.common.operator.minusAssign
import io.github.jisungbin.gitmessengerbot.common.operator.plusAssign
import io.github.sungbin.gitmessengerbot.core.Injection
import io.github.sungbin.gitmessengerbot.core.bot.debug.DebugStore
import io.github.sungbin.gitmessengerbot.core.bot.debug.createDebugItem
import io.github.sungbin.gitmessengerbot.core.bot.script.ScriptItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object Sender {
    const val Bot = "Bot"

    @Suppress("FunctionName")
    fun User(name: String = "User") = name
}

object Bot {
    private val _scripts = MutableStateFlow(getList())
    private val scriptPowers: HashMap<Int, MutableStateFlow<Boolean>> = hashMapOf()
    private val compileStates: HashMap<Int, MutableStateFlow<Boolean>> = hashMapOf()
    private val scriptCompiler = Injection.Compiler.provide

    val scripts = _scripts.asStateFlow()

    suspend fun compileScript(context: Context, script: ScriptItem) =
        scriptCompiler.process(context, script)

    fun getAllScripts() = scripts.value

    fun getCompiledScripts() = _scripts.value.filter { it.compiled }

    fun getRunnableScripts() = _scripts.value.filter { it.isRunnable }

    fun getScriptPower(script: ScriptItem) = scriptPowers[script.id]?.asStateFlow()
        ?: throw CoreException("scriptPowers에 ${script.id} 키가 없어요.")

    fun getCompileState(script: ScriptItem) = compileStates[script.id]?.asStateFlow()
        ?: throw CoreException("compileStates에 ${script.id} 키가 없어요.")

    fun scriptDataSave(script: ScriptItem) {
        _scripts.edit {
            removeIf { it.id == script.id }
            add(script)
        }
        Storage.write(
            ScriptConstant.ScriptDataPath(script.name, script.lang),
            script.toJsonString()
        )
    }

    fun scriptCodeSave(script: ScriptItem, code: String) {
        Storage.write(ScriptConstant.ScriptPath(script.name, script.lang), code)
    }

    fun addScript(script: ScriptItem) {
        _scripts += script
        script.add()
    }

    fun removeScript(script: ScriptItem) {
        _scripts -= script
        script.delete()
    }

    fun replyToSession(context: Context, session: NotificationCompat.Action, message: String) {
        try {
            val sendIntent = Intent()
            val messageBundle = Bundle()
            val remoteInputs =
                session.remoteInputs ?: throw CoreException("remoteInputs가 null 이에요.")
            for (inputable in remoteInputs) {
                messageBundle.putCharSequence(inputable.resultKey, message)
            }
            RemoteInput.addResultsToIntent(session.remoteInputs, sendIntent, messageBundle)
            session.actionIntent.send(context, 0, sendIntent)
        } catch (exception: Exception) {
            throw CoreException(exception.message)
        }
    }

    // TODO: Profile Image
    fun callJsResponder(
        script: ScriptItem,
        room: String,
        message: String,
        sender: String,
        isGroupChat: Boolean,
        isDebugMode: Boolean = false,
    ) {
        try {
            val v8 = StackManager.v8[script.id] ?: run {
                Log.i("callJsResponder", "${script.name} v8 instance is null.")
                return
            }
            v8.locker.acquire()
            if (script.id == ScriptConstant.EvalId) {
                val result = v8.executeScript(message).toString()
                DebugStore.add(createDebugItem(ScriptConstant.EvalId, result, "null", Sender.Bot))
            } else {
                val arguments =
                    listOf(room, message, sender, isGroupChat, "null", isDebugMode)
                v8.executeJSFunction(
                    ScriptConstant.DefaultResponseFunctionName,
                    *arguments.toTypedArray()
                )
            }
            v8.locker.release()
            Log.i("callJsResponder", "Run: ${script.name}")
        } catch (exception: Exception) {
            throw CoreException(exception.message)
        }
    }

    private fun getList(): List<ScriptItem> {
        val scripts = mutableListOf<ScriptItem>()
        repeat(4) { lang -> // 스크립트 코드파일이 아니라, 스크립트 정보 파일을 읽어와야함
            Storage.fileList(ScriptConstant.ScriptListPath(lang))
                .filter { it.path.endsWith(".json") }
                .forEach { scriptDataFile ->
                    val scriptData = Storage.read(scriptDataFile.path, null)
                        ?: throw CoreException("$scriptDataFile 파일이 null 이에요.")
                    val script: ScriptItem = scriptData.toModel()
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
}
