/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [MviJsSideEffect.kt] created by Ji Sungbin on 21. 9. 23. 오후 7:31
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.editor.js.mvi

sealed class MviJsEditorSideEffect {
    data class UpdateCodeField(val code: String) : MviJsEditorSideEffect()
    data class LoadCommitHistory(val commit)
}
