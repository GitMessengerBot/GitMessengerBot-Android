/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ScriptConfig.kt] created by Ji Sungbin on 21. 8. 30. 오후 5:02
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.common.constant

import io.github.jisungbin.gitmessengerbot.common.script.getScriptSuffix
import io.github.jisungbin.gitmessengerbot.common.script.toScriptLangName
import kotlin.random.Random

@Suppress("FunctionName")
object ScriptConstant {
    const val DebugAllBot = -1
    const val EvalId = -2

    const val DefaultResponseFunctionName = "onMessage"
    const val DebugAllPath = "${PathConstant.AppStorage}/debug"

    fun DebugDataPath(scriptId: Int) =
        "${PathConstant.AppStorage}/debug/$scriptId/${Random.nextInt()}.json"

    fun ScriptPath(name: String, lang: Int) =
        "${PathConstant.AppStorage}/scripts/${lang.toScriptLangName()}/$name.${lang.getScriptSuffix()}"

    fun ScriptListPath(lang: Int) = "${PathConstant.AppStorage}/scripts/${lang.toScriptLangName()}"

    fun ScriptDataPath(name: String, lang: Int) =
        "${PathConstant.AppStorage}/scripts/${lang.toScriptLangName()}/$name-data.json"
}
