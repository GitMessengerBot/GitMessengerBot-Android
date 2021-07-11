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

@Suppress("FunctionName")
object PathConfig {
    private const val AppStorage = "GitMessengerBot/data"

    const val GithubData = "$AppStorage/github-data.json"
    const val AppData = "$AppStorage/app.json"
    const val IntentScriptId = "intent-script-id"
    const val ScriptDefaultClass = "onMessage"

    /**
     * 스크립트 코드 파일
     *
     * "$AppStorage/scripts/${lang.toScriptLangName()}/$name.${lang.toScriptSuffix()}"
     */
    fun Script(name: String, lang: Int) =
        "$AppStorage/scripts/${lang.toScriptLangName()}/$name.${lang.toScriptSuffix()}"

    /**
     * 스크립트 리스트 경로
     *
     * "$AppStorage/scripts/${lang.toScriptLangName()}"
     */
    fun ScriptPath(lang: Int) = "$AppStorage/scripts/${lang.toScriptLangName()}"

    /**
     * 스크립트 json 데이터 파일
     *
     *  "$AppStorage/scripts/${lang.toScriptLangName()}/$name-data.json"
     */
    fun ScriptData(name: String, lang: Int) =
        "$AppStorage/scripts/${lang.toScriptLangName()}/$name-data.json"

    /**
     * 스크립트 클레스 파일
     *
     * "$AppStorage/scripts/${lang.toScriptLangName()}/$scriptName-$className.${lang.toScriptSuffix()}"
     */
    fun ScriptClass(scriptName: String, lang: Int, className: String) =
        "$AppStorage/scripts/${lang.toScriptLangName()}/$scriptName/class/$className.${lang.toScriptSuffix()}"

    /**
     * 스크립트 클레스 파일 경로
     *
     * "$AppStorage/scripts/${lang.toScriptLangName()}/$scriptName-$className.${lang.toScriptSuffix()}"
     */
    fun ScriptClassPath(scriptName: String, lang: Int) =
        "$AppStorage/scripts/${lang.toScriptLangName()}/$scriptName/class"
}
