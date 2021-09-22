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
import io.github.jisungbin.gitmessengerbot.common.config.ScriptConfig
import io.github.jisungbin.gitmessengerbot.common.exception.CoreException
import io.github.jisungbin.gitmessengerbot.common.script.ScriptLang
import io.github.sungbin.gitmessengerbot.core.CoreResult
import io.github.sungbin.gitmessengerbot.core.bot.Bot
import io.github.sungbin.gitmessengerbot.core.bot.StackManager
import io.github.sungbin.gitmessengerbot.core.bot.api.BotApi
import io.github.sungbin.gitmessengerbot.core.bot.api.Log
import io.github.sungbin.gitmessengerbot.core.bot.api.UI
import io.github.sungbin.gitmessengerbot.core.bot.script.ScriptItem
import io.github.sungbin.gitmessengerbot.core.bot.script.ts2js.repo.Ts2JsRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect

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
        CoreResult.Success(Unit)
    } catch (exception: Exception) {
        script.power = false
        script.compiled = false
        CoreResult.Fail(exception)
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

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun process(context: Context, script: ScriptItem) = callbackFlow {
        when (script.lang) {
            ScriptLang.TypeScript -> {
                ts2Js
                    .convert(script.getCode())
                    .collect { ts2JsResult ->
                        when (ts2JsResult) {
                            is CoreResult.Success -> {
                                val jsCode = ts2JsResult.response.jsCode
                                android.util.Log.i("ts2JsResult", jsCode)
                                trySend(compileJavaScript(context, script, jsCode))
                            }
                            is CoreResult.Fail -> {
                                script.power = false
                                script.compiled = false
                                trySend(CoreResult.Fail(ts2JsResult.exception))
                            }
                        }
                    }
            }
            ScriptLang.JavaScript -> {
                trySend(compileJavaScript(context, script, script.getCode()))
            }
            ScriptLang.Python -> { // todo
                trySend(CoreResult.Fail(CoreException("Python build is TODO.")))
            }
            ScriptLang.Simple -> { // todo
                trySend(CoreResult.Fail(CoreException("SimpleLang build is TODO.")))
            }
        }

        if (script.id != ScriptConfig.EvalId) {
            Bot.scriptDataSave(script)
        }

        close()
    }
}
