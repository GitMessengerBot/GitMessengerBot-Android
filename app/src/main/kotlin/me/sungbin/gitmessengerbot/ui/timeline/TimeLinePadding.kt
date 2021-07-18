/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [TimeLinePadding.kt] created by Ji Sungbin on 21. 7. 19. 오전 2:31.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.ui.timeline

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class TimeLinePadding(
    val contentStart: Dp = 4.dp,
    val contentTop: Dp = 4.dp,
    val contentBottom: Dp = 4.dp,
    val circleLineGap: Dp = 1.dp
)
