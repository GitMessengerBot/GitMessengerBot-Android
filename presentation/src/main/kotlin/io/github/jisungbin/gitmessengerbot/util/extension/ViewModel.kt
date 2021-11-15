/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ViewModel.kt] created by Ji Sungbin on 21. 11. 15. 오후 6:12
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.util.extension

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
inline fun <reified VM : ViewModel> composableActivityViewModel(
    key: String? = null,
    factory: ViewModelProvider.Factory? = null
): VM = viewModel(
    VM::class.java,
    LocalContext.current as ComponentActivity,
    key,
    factory
)
