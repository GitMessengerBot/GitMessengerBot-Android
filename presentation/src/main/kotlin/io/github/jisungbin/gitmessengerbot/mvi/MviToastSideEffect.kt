/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [MviBaseState.kt] created by Ji Sungbin on 21. 9. 18. 오후 8:56
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.mvi

interface MviToastSideEffect {
    data class Toast(val message: String) : MviToastSideEffect
}
