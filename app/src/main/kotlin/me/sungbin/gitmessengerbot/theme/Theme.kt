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
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import me.sungbin.gitmessengerbot.R

val twiceLightGray = Color(236, 236, 236)
val defaultFontFamily = FontFamily(Font(R.font.nanumbarungothic))

val typography = Typography(defaultFontFamily = defaultFontFamily)

val colors = lightColors().copy(
    primary = Color(0xFF942ccc),
    primaryVariant = Color(0xFF60009a),
    secondary = Color(0xFFc960ff),
    secondaryVariant = Color(0xFF942ccb)
)

@Composable
fun MaterialTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = typography,
        colors = colors
    ) {
        content()
    }
}
