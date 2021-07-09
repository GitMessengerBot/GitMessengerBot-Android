/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Theme.kt] created by Ji Sungbin on 21. 5. 21. 오후 4:37.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val twiceLightGray = Color(236, 236, 236)

val colors = lightColors().copy(
    primary = Color(0xFF6b1aa5),
    primaryVariant = Color(0xFF380075),
    secondary = Color(0xFF9e4dd7)
)

@Composable
fun MaterialTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = colors
    ) {
        content()
    }
}
