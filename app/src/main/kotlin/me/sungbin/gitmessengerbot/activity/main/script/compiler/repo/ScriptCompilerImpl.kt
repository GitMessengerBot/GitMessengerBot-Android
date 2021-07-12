/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ScriptCompilerImpl.kt] created by Ji Sungbin on 21. 7. 12. 오후 9:59.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.script.compiler.repo

import android.content.Context
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import me.sungbin.gitmessengerbot.activity.main.script.CompileResult
import me.sungbin.gitmessengerbot.activity.main.script.ScriptItem
import me.sungbin.gitmessengerbot.activity.main.script.ScriptLang
import me.sungbin.gitmessengerbot.activity.main.script.ts2js.repo.Ts2JsRepo
import me.sungbin.gitmessengerbot.activity.main.script.ts2js.repo.Ts2JsResult
import me.sungbin.gitmessengerbot.bot.Bot
import me.sungbin.gitmessengerbot.bot.StackManager
import me.sungbin.gitmessengerbot.bot.api.BotApi
import me.sungbin.gitmessengerbot.bot.api.Log

class ScriptCompilerImpl @Inject constructor(
    private val ts2Js: Ts2JsRepo
) : ScriptCompiler {

    private fun compileJavaScript(
        context: Context,
        script: ScriptItem,
        code: String
    ): CompileResult {
        return try {
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
            script.compiled = true
            v8.locker.release()
            CompileResult.Success
        } catch (exception: Exception) {
            script.power = false
            script.compiled = false
            CompileResult.Error(exception)
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

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun process(context: Context, script: ScriptItem) = callbackFlow {
        when (script.lang) {
            ScriptLang.TypeScript -> {
                ts2Js
                    .convert(Bot.getCode(script))
                    .collect { ts2JsResult ->
                        when (ts2JsResult) {
                            is Ts2JsResult.Success -> {
                                val tsCode = ts2JsResult.ts2js.tsCode
                                println(tsCode)
                                trySend(compileJavaScript(context, script, tsCode))
                            }
                            is Ts2JsResult.Error -> {
                                script.power = false
                                script.compiled = false
                                trySend(CompileResult.Error(ts2JsResult.exception))
                            }
                        }
                    }
            }
            ScriptLang.JavaScript -> {
                trySend(compileJavaScript(context, script, Bot.getCode(script)))
            }
            ScriptLang.Python -> { // todo
                trySend(CompileResult.Error(Exception("TODO")))
            }
            ScriptLang.Simple -> { // todo
                trySend(CompileResult.Error(Exception("TODO")))
            }
        }

        Bot.save(script)
        awaitClose { close() }
    }
}
