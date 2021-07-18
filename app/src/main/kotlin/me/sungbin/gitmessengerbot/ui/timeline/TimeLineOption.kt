/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [TimeLineOption.kt] created by Ji Sungbin on 21. 7. 19. 오전 2:27.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.ui.timeline

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.theme.colors

data class TimeLineOption(
    @DrawableRes val circleIcon: Int = R.drawable.ic_outline_circle_24,
    val circleSize: Dp = Dp.Unspecified,
    val circleColor: Color = Color.Gray,
    val lineColor: Color = colors.primary,
    val lineWidth: Dp = 2.dp,
    val contentHeight: Dp = 100.dp
)
