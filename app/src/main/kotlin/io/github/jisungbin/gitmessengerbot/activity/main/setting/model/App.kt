/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [App.kt] created by Ji Sungbin on 21. 7. 11. 오전 4:02.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.main.setting.model

import io.github.jisungbin.gitmessengerbot.activity.main.script.ScriptLang
import io.github.jisungbin.gitmessengerbot.util.config.StringConfig

data class App(
    var power: Boolean = false,
    var evalMode: Boolean = false,
    var editorFontSize: Int = 13,
    var editorAutoSave: Int = 5,
    var scriptDefaultCode: ScriptDefaultCode = ScriptDefaultCode(),
    var scriptDefaultLang: Int = ScriptLang.TypeScript,
    var scriptResponseFunctioName: String = StringConfig.ScriptResponseFunctionName,
    var gitDefaultBranch: String = StringConfig.GitDefaultBranch,
    var gitDefaultCommitMessage: String = StringConfig.GitDefaultCommitMessage,
    var gitDefaultRepoOptions: RepoOptions = RepoOptions(),
    val kakaoTalkPackageNames: MutableList<String> = mutableListOf(StringConfig.KakaoTalkDefaultPackageName),
)
