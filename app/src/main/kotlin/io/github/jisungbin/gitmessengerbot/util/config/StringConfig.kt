/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [PathManager.kt] created by Ji Sungbin on 21. 6. 14. 오후 7:11.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.util.config

import io.github.jisungbin.gitmessengerbot.activity.main.script.toScriptLangName
import io.github.jisungbin.gitmessengerbot.activity.main.script.toScriptSuffix
import kotlin.random.Random

@Suppress("FunctionName")
object StringConfig {
    private const val AppStorage = "GitMessengerBot/data"
    const val AppData = "$AppStorage/app.json"
    const val KakaoTalkDefaultPackageName = "com.kakao.talk"

    const val GithubData = "$AppStorage/github-data.json"
    const val GitDefaultBranch = "main"
    const val GitDefaultRepoDescription = "Created by GitMessengerBot"
    const val GitDefaultCommitMessage = "Commited by GitMessengerBot"

    const val IntentScriptId = "intent-script-id"
    const val IntentNotificationAction = "intent-notifiaction-action"
    const val IntentBotPowerToggle = "intent-bot-power-onoff"
    const val IntentBotRecompile = "intent-bot-recompile"
    const val IntentImageUrl = "intent-image-url"
    const val IntentDebugScriptId = "intent-debug-script-id"

    const val DebugAllBot = -1

    const val ScriptEvalId = -2
    const val ScriptResponseFunctionName = "onMessage"

    /**
     * 스크립트 디버그 파일
     */
    fun Debug(scriptId: Int) = "$AppStorage/debug/$scriptId/${Random.nextInt()}.json"

    /**
     * 디버그 저장소 경로
     *
     * "$AppStorage/debug"
     */
    fun DebugPath() = "$AppStorage/debug"

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
}
