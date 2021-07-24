/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ScriptCompilerImpl.kt] created by Ji Sungbin on 21. 7. 12. 오후 9:59.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.main.script.compiler.repo

import android.content.Context
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import io.github.jisungbin.gitmessengerbot.activity.main.script.ScriptItem
import io.github.jisungbin.gitmessengerbot.activity.main.script.ScriptLang
import io.github.jisungbin.gitmessengerbot.activity.main.script.ts2js.Ts2JsResponse
import io.github.jisungbin.gitmessengerbot.activity.main.script.ts2js.repo.Ts2JsRepo
import io.github.jisungbin.gitmessengerbot.bot.Bot
import io.github.jisungbin.gitmessengerbot.bot.StackManager
import io.github.jisungbin.gitmessengerbot.bot.api.BotApi
import io.github.jisungbin.gitmessengerbot.bot.api.Log
import io.github.jisungbin.gitmessengerbot.repo.Result
import io.github.jisungbin.gitmessengerbot.util.Nothing
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect

class ScriptCompilerImpl @Inject constructor(
    private val ts2Js: Ts2JsRepo
) : ScriptCompiler {

    private fun compileJavaScript(
        context: Context,
        script: ScriptItem,
        code: String
    ): Result {
        return try {
            val v8 = V8.createV8Runtime()
            v8.addApi(
                apiName = "Bot",
                apiClass = BotApi(context = context, scriptId = script.id),
                methodNames = listOf("reply", "replyShowAll"),
                argumentsList = listOf(
                    listOf(String::class.java, String::class.java, Boolean::class.java),
                    listOf(
                        String::class.java,
                        String::class.java,
                        String::class.java,
                        Boolean::class.java
                    )
                )
            )
            v8.addApi(
                apiName = "Log",
                apiClass = Log(),
                methodNames = listOf("print"),
                argumentsList = listOf(listOf(Any::class.java))
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
            Result.Success(Nothing())
        } catch (exception: Exception) {
            script.power = false
            script.compiled = false
            Result.Fail(exception)
        }
    }

    @Suppress("DEPRECATION")
    private fun V8.addApi(
        apiName: String,
        apiClass: Any,
        methodNames: List<String>,
        argumentsList: List<List<Class<*>>>
    ) {
        val api = V8Object(this)
        this.add(apiName, api)

        for ((index, methodName) in methodNames.withIndex()) {
            api.registerJavaMethod(
                apiClass,
                methodName,
                methodName,
                argumentsList[index].toTypedArray()
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
                            is Result.Success -> {
                                val tsCode = (ts2JsResult.response as Ts2JsResponse).tsCode
                                println(tsCode)
                                trySend(compileJavaScript(context, script, tsCode))
                            }
                            is Result.Fail -> {
                                script.power = false
                                script.compiled = false
                                trySend(Result.Fail(ts2JsResult.exception))
                            }
                        }
                    }
            }
            ScriptLang.JavaScript -> {
                trySend(compileJavaScript(context, script, Bot.getCode(script)))
            }
            ScriptLang.Python -> { // todo
                trySend(Result.Fail(Exception("TODO")))
            }
            ScriptLang.Simple -> { // todo
                trySend(Result.Fail(Exception("TODO")))
            }
        }

        Bot.save(script)
        awaitClose { close() }
    }
}
