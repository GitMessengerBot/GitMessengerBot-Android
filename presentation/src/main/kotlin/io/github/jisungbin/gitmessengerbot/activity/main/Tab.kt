/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Screen.kt] created by Ji Sungbin on 21. 9. 22. 오후 7:51
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.main

import androidx.annotation.DrawableRes
import io.github.jisungbin.gitmessengerbot.R

sealed class Tab(val route: String, @DrawableRes val iconRes: Int) {
    object Script : Tab("Script", R.drawable.ic_round_script_24)
    object Debug : Tab("Debug", R.drawable.ic_round_debug_24)
    object Kaven : Tab("Kaven", R.drawable.ic_round_github_24)
    object Setting : Tab("Setting", R.drawable.ic_round_settings_24)
}
