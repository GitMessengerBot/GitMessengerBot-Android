/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [PathManager.kt] created by Ji Sungbin on 21. 6. 14. 오후 7:11.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.util.config

import me.sungbin.gitmessengerbot.activity.main.script.toScriptLangName
import me.sungbin.gitmessengerbot.activity.main.script.toScriptSuffix

object PathConfig {
    private const val AppStorage = "GitMessengerBot/data"
    const val GithubData = "$AppStorage/github-data.json"
    const val IntentScriptId = "intent-script-id"

    val Script =
        { name: String, lang: Int -> "$AppStorage/scripts/${lang.toScriptLangName()}/$name.${lang.toScriptSuffix()}" }
    val ScriptPath = { lang: Int -> "$AppStorage/scripts/${lang.toScriptLangName()}" }
    val ScriptData =
        { name: String, lang: Int -> "$AppStorage/scripts/${lang.toScriptLangName()}/$name-data.json" }
}
