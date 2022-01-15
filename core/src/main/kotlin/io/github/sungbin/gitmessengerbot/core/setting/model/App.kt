/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [App.kt] created by Ji Sungbin on 21. 8. 29. 오후 9:14
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.setting.model

import io.github.jisungbin.gitmessengerbot.common.constant.BotConstant
import io.github.jisungbin.gitmessengerbot.common.constant.EditorConstant
import io.github.jisungbin.gitmessengerbot.common.constant.GithubConstant
import io.github.jisungbin.gitmessengerbot.common.constant.ScriptConstant
import io.github.jisungbin.gitmessengerbot.common.script.ScriptLang

data class App(
    val power: Boolean = false,
    val evalMode: Boolean = false,
    val editorHorizontalScroll: Boolean = EditorConstant.HorizontalScroll,
    val editorFontName: String = EditorConstant.FontName,
    val editorFontSize: Int = EditorConstant.FontSize,
    val editorAutoSave: Int = EditorConstant.AutoSave,
    val scriptDefaultCode: ScriptDefaultCode = ScriptDefaultCode(),
    val scriptDefaultLang: Int = ScriptLang.TypeScript,
    val scriptResponseFunctionName: String = ScriptConstant.DefaultResponseFunctionName,
    val gitDefaultBranch: String = GithubConstant.DefaultBranch,
    val gitDefaultCommitMessage: String = GithubConstant.DefaultCommitMessage,
    val gitDefaultRepoOptions: RepoOptions = RepoOptions(),
    val kakaoTalkPackageNames: List<String> = listOf(BotConstant.KakaoTalkDefaultPackageName),
)
