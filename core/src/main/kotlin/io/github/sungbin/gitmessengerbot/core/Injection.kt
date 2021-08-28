/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Injection.kt] created by Ji Sungbin on 21. 8. 28. 오후 11:51
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core

import io.github.sungbin.gitmessengerbot.core.bot.script.compiler.repo.ScriptCompiler
import io.github.sungbin.gitmessengerbot.core.bot.script.compiler.repo.ScriptCompilerImpl
import io.github.sungbin.gitmessengerbot.core.bot.script.ts2js.repo.Ts2JsRepo
import io.github.sungbin.gitmessengerbot.core.bot.script.ts2js.repo.Ts2JsRepoImpl
import org.jsoup.Jsoup

object Injection {
    object Ts2Js {
        private const val BaseUrl = "https://api.extendsclass.com/convert/typescript/javascript"
        private val jsoup = Jsoup.connect(BaseUrl)

        val provide: Ts2JsRepo = Ts2JsRepoImpl(jsoup)
    }

    object Compiler {
        val provide: ScriptCompiler = ScriptCompilerImpl(Ts2Js.provide)
    }
}
