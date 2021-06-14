/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Compose.kt] created by Ji Sungbin on 21. 6. 14. 오후 10:00.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.util.extension

import androidx.annotation.FontRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

@Composable
fun fontResource(@FontRes font: Int) = FontFamily(Font(font))
