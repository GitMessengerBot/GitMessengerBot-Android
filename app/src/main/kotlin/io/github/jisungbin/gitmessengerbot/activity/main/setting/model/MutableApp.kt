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
    var power: MutableState<Boolean> = mutableStateOf(false),
    var evalMode: MutableState<Boolean> = mutableStateOf(false),
    var editorFontSize: MutableState<Int> = mutableStateOf(13),
    var editorAutoSave: MutableState<Int> = mutableStateOf(5),
    var scriptDefaultCode: MutableState<ScriptDefaultCode> = mutableStateOf(ScriptDefaultCode()),
    var scriptDefaultLang: MutableState<Int> = mutableStateOf(ScriptLang.TypeScript),
    var scriptResponseFunctionName: MutableState<String> = mutableStateOf(StringConfig.ScriptResponseFunctionName),
    var gitDefaultBranch: MutableState<String> = mutableStateOf(StringConfig.GitDefaultBranch),
    var gitDefaultCommitMessage: MutableState<String> = mutableStateOf(StringConfig.GitDefaultCommitMessage),
    var gitDefaultRepoOptions: MutableState<RepoOptions> = mutableStateOf(RepoOptions()),
    val kakaoTalkPackageNames: SnapshotStateList<String> = mutableStateListOf(StringConfig.KakaoTalkDefaultPackageName),
    val useHorizontalScroll: MutableState<Boolean> = mutableStateOf(true)
)

data class App(
    var power: Boolean = false,
    var evalMode: Boolean = false,
    var editorFontSize: Int = 13,
    var editorAutoSave: Int = 5,
    var scriptDefaultCode: ScriptDefaultCode = ScriptDefaultCode(),
    var scriptDefaultLang: Int = ScriptLang.TypeScript,
    var scriptResponseFunctionName: String = StringConfig.ScriptResponseFunctionName,
    var gitDefaultBranch: String = StringConfig.GitDefaultBranch,
    var gitDefaultCommitMessage: String = StringConfig.GitDefaultCommitMessage,
    var gitDefaultRepoOptions: RepoOptions = RepoOptions(),
    val kakaoTalkPackageNames: List<String> = listOf(StringConfig.KakaoTalkDefaultPackageName),
    val useHorizontalScroll: Boolean = true
)
