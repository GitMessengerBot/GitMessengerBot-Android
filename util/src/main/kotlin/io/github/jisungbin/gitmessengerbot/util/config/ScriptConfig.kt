/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [PathConfig.kt] created by Ji Sungbin on 21. 8. 28. 오후 2:34.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.util.config

import io.github.jisungbin.gitmessengerbot.util.script.getScriptSuffix
import io.github.jisungbin.gitmessengerbot.util.script.toScriptLangName
import kotlin.random.Random

object ScriptConfig {
    const val DebugAllBot = -1
    const val EvalId = -2
    const val ResponseFunctionName = "onMessage"

    const val DebugAllPath = "${PathConfig.AppStorage}/debug"

    fun DebugDataPath(scriptId: Int) =
        "${PathConfig.AppStorage}/debug/$scriptId/${Random.nextInt()}.json"

    fun ScriptPath(name: String, lang: Int) =
        "${PathConfig.AppStorage}/scripts/${lang.toScriptLangName()}/$name.${lang.getScriptSuffix()}"

    fun ScriptListPath(lang: Int) = "${PathConfig.AppStorage}/scripts/${lang.toScriptLangName()}"

    fun ScriptDataPath(name: String, lang: Int) =
        "${PathConfig.AppStorage}/scripts/${lang.toScriptLangName()}/$name-data.json"
}
