/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [App.kt] created by Ji Sungbin on 21. 8. 29. 오후 9:14
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.setting.model

import io.github.jisungbin.gitmessengerbot.util.config.Config
import io.github.jisungbin.gitmessengerbot.util.config.EditorConfig
import io.github.jisungbin.gitmessengerbot.util.config.GithubConfig
import io.github.jisungbin.gitmessengerbot.util.config.ScriptConfig
import io.github.jisungbin.gitmessengerbot.util.script.ScriptLang

data class App(
    val power: Boolean = false,
    val evalMode: Boolean = false,
    val editorHorizontalScroll: Boolean = EditorConfig.HorizontalScroll,
    val editorFontName: String = EditorConfig.FontName,
    val editorFontSize: Int = EditorConfig.FontSize,
    val editorAutoSave: Int = EditorConfig.AutoSave,
    val scriptDefaultCode: ScriptDefaultCode = ScriptDefaultCode(),
    val scriptDefaultLang: Int = ScriptLang.TypeScript,
    val scriptResponseFunctionName: String = ScriptConfig.DefaultResponseFunctionName,
    val gitDefaultBranch: String = GithubConfig.DefaultBranch,
    val gitDefaultCommitMessage: String = GithubConfig.DefaultCommitMessage,
    val gitDefaultRepoOptions: RepoOptions = RepoOptions(),
    val kakaoTalkPackageNames: List<String> = listOf(Config.KakaoTalkDefaultPackageName),
)
