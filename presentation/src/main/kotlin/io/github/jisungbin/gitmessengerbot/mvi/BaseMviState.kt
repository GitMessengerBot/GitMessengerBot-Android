/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [BaseMviState.kt] created by Ji Sungbin on 21. 9. 19. 오후 4:47
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.mvi

interface BaseMviState {
    val loaded: Boolean
    val exception: Exception?

    fun isException() = exception != null
}
