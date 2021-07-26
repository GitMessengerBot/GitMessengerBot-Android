/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [App.kt] created by Ji Sungbin on 21. 7. 11. 오전 4:02.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.main.setting.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import io.github.jisungbin.gitmessengerbot.activity.main.script.ScriptLang
import io.github.jisungbin.gitmessengerbot.util.config.StringConfig

data class MutableApp(
    val power: MutableState<Boolean> = mutableStateOf(false),
    val evalMode: MutableState<Boolean> = mutableStateOf(false),
    val editorHorizontalScroll: MutableState<Boolean> = mutableStateOf(true),
    val editorFontName: MutableState<String> = mutableStateOf("d2coding"),
    val editorFontSize: MutableState<Int> = mutableStateOf(13),
    val editorAutoSave: MutableState<Int> = mutableStateOf(5),
    val scriptDefaultCode: MutableState<ScriptDefaultCode> = mutableStateOf(ScriptDefaultCode()),
    val scriptDefaultLang: MutableState<Int> = mutableStateOf(ScriptLang.TypeScript),
    val scriptResponseFunctionName: MutableState<String> = mutableStateOf(StringConfig.ScriptResponseFunctionName),
    val gitDefaultBranch: MutableState<String> = mutableStateOf(StringConfig.GitDefaultBranch),
    val gitDefaultCommitMessage: MutableState<String> = mutableStateOf(StringConfig.GitDefaultCommitMessage),
    val gitDefaultRepoOptions: MutableState<RepoOptions> = mutableStateOf(RepoOptions()),
    val kakaoTalkPackageNames: SnapshotStateList<String> = mutableStateListOf(StringConfig.KakaoTalkDefaultPackageName),
)

data class App(
    val power: Boolean = false,
    val evalMode: Boolean = false,
    val editorHorizontalScroll: Boolean = true,
    val editorFontName: String,
    val editorFontSize: Int = 13,
    val editorAutoSave: Int = 5,
    val scriptDefaultCode: ScriptDefaultCode = ScriptDefaultCode(),
    val scriptDefaultLang: Int = ScriptLang.TypeScript,
    val scriptResponseFunctionName: String = StringConfig.ScriptResponseFunctionName,
    val gitDefaultBranch: String = StringConfig.GitDefaultBranch,
    val gitDefaultCommitMessage: String = StringConfig.GitDefaultCommitMessage,
    val gitDefaultRepoOptions: RepoOptions = RepoOptions(),
    val kakaoTalkPackageNames: List<String> = listOf(StringConfig.KakaoTalkDefaultPackageName),
)