/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [App.kt] created by Ji Sungbin on 21. 8. 29. 오후 9:14
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.setting.model

import io.github.jisungbin.gitmessengerbot.common.config.Config
import io.github.jisungbin.gitmessengerbot.common.config.EditorConfig
import io.github.jisungbin.gitmessengerbot.common.config.GithubConfig
import io.github.jisungbin.gitmessengerbot.common.config.ScriptConfig
import io.github.jisungbin.gitmessengerbot.common.script.ScriptLang

data class App(
    val power: Boolean = false,
    val evalMode: Boolean = false,
    val editorHorizontalScroll: Boolean = io.github.jisungbin.gitmessengerbot.common.config.EditorConfig.HorizontalScroll,
    val editorFontName: String = io.github.jisungbin.gitmessengerbot.common.config.EditorConfig.FontName,
    val editorFontSize: Int = io.github.jisungbin.gitmessengerbot.common.config.EditorConfig.FontSize,
    val editorAutoSave: Int = io.github.jisungbin.gitmessengerbot.common.config.EditorConfig.AutoSave,
    val scriptDefaultCode: ScriptDefaultCode = ScriptDefaultCode(),
    val scriptDefaultLang: Int = ScriptLang.TypeScript,
    val scriptResponseFunctionName: String = io.github.jisungbin.gitmessengerbot.common.config.ScriptConfig.DefaultResponseFunctionName,
    val gitDefaultBranch: String = io.github.jisungbin.gitmessengerbot.common.config.GithubConfig.DefaultBranch,
    val gitDefaultCommitMessage: String = io.github.jisungbin.gitmessengerbot.common.config.GithubConfig.DefaultCommitMessage,
    val gitDefaultRepoOptions: RepoOptions = RepoOptions(),
    val kakaoTalkPackageNames: List<String> = listOf(io.github.jisungbin.gitmessengerbot.common.config.Config.KakaoTalkDefaultPackageName),
)
