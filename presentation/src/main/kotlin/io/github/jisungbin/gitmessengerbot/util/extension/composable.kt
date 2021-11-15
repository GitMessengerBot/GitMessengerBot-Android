/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [composable.kt] created by Ji Sungbin on 21. 11. 15. 오후 8:28
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.util.extension

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Suppress("NOTHING_TO_INLINE")
@Composable
inline fun getActivity() = LocalContext.current as ComponentActivity
