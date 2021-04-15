/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.ui.fancybottombar

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Created by Ji Sungbin on 2021/04/15.
 */

class FancyOptions(
    var fontFamily: FontFamily = FontFamily.Default,
    var indicatorHeight: Dp = 1.dp,
    var barHeight: Dp = 60.dp,
    var titleTopPadding: Dp = 4.dp
)
