/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ScriptCompilerImpl.kt] created by Ji Sungbin on 21. 8. 28. 오후 11:59
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.bot.script.compiler.repo

import android.content.Context
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import io.github.jisungbin.gitmessengerbot.common.constant.ScriptConstant
import io.github.jisungbin.gitmessengerbot.common.exception.TodoException
import io.github.jisungbin.gitmessengerbot.common.extension.doWhen
import io.github.jisungbin.gitmessengerbot.common.script.ScriptLang
import io.github.sungbin.gitmessengerbot.core.bot.Bot
import io.github.sungbin.gitmessengerbot.core.bot.StackManager
import io.github.sungbin.gitmessengerbot.core.bot.api.BotApi
import io.github.sungbin.gitmessengerbot.core.bot.api.Log
import io.github.sungbin.gitmessengerbot.core.bot.api.UI
import io.github.sungbin.gitmessengerbot.core.bot.script.ScriptItem
import io.github.sungbin.gitmessengerbot.core.bot.script.ts2js.repo.Ts2JsRepo
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

internal class ScriptCompilerImpl(private val ts2Js: Ts2JsRepo) : ScriptCompiler {
    private fun compileJavaScript(
        context: Context,
        script: ScriptItem,
        code: String,
    ) = try {
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
        v8.addApi(
            apiName = "UI",
            apiClass = UI(context),
            methodNames = listOf("toast"),
            argumentsList = listOf(listOf(String::class.java))
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
        )*/
        v8.executeScript(code)
        StackManager.v8[script.id] = v8
        script.compiled = true
        v8.locker.release()
        Result.success(Unit)
    } catch (exception: Exception) {
        script.power = false
        script.compiled = false
        Result.failure(exception)
    }

    @Suppress("DEPRECATION")
    private fun V8.addApi(
        apiName: String,
        apiClass: Any,
        methodNames: List<String>,
        argumentsList: List<List<Class<*>>>,
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

    override suspend fun process(context: Context, script: ScriptItem): Result<Unit> =
        suspendCancellableCoroutine { continuation ->
            when (script.lang) {
                ScriptLang.TypeScript -> suspend {
                    ts2Js
                        .convert(script.getCode())
                        .doWhen(
                            onSuccess = { ts2JsResult ->
                                val jsCode = ts2JsResult.jsCode
                                android.util.Log.i("ts2JsResult", jsCode)
                                continuation.resume(
                                    compileJavaScript(context, script, jsCode)
                                )
                            },
                            onFailure = { exception ->
                                script.power = false
                                script.compiled = false
                                continuation.resume(Result.failure(exception))
                            }
                        )
                }
                ScriptLang.JavaScript -> {
                    continuation.resume(compileJavaScript(context, script, script.getCode()))
                }
                ScriptLang.Python -> { // todo
                    continuation.resume(Result.failure(TodoException("파이썬 언어")))
                }
                ScriptLang.Simple -> { // todo
                    continuation.resume(Result.failure(TodoException("단자응 언어")))
                }
            }

            if (script.id != ScriptConstant.EvalId) {
                Bot.scriptDataSave(script)
            }
        }
}
