/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [MviSetupState.kt] created by Ji Sungbin on 21. 9. 19. 오후 5:12
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.setup

import io.github.jisungbin.gitmessengerbot.mvi.MviBaseState

data class MviSetupState(
    override val loading: Boolean = true,
    override val exception: Exception? = null,
    val aouthToken: String = "",
    val userName: String = "",
    val profileImageUrl: String = "",
) : MviBaseState
