/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [MviJsSideEffect.kt] created by Ji Sungbin on 21. 9. 23. 오후 7:31
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.editor.js.mvi

import io.github.jisungbin.gitmessengerbot.activity.editor.js.CommitHistoryItem
import io.github.jisungbin.gitmessengerbot.mvi.BaseMviToastSideEffect

sealed class BaseMviJsEditorSideEffect : BaseMviToastSideEffect {
    data class UpdateCodeField(val code: String) : BaseMviJsEditorSideEffect()
    data class UpdateCommitHistoryItems(val commitHistoryItems: List<CommitHistoryItem>) :
        BaseMviJsEditorSideEffect()
}
