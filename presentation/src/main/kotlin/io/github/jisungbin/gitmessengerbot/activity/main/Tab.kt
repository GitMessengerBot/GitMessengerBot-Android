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

enum class Tab(val route: String, @DrawableRes val iconRes: Int) {
    Script("Script", R.drawable.ic_round_script_24),
    Debug("Debug", R.drawable.ic_round_debug_24),
    Kaven("Kaven", R.drawable.ic_round_github_24),
    Setting("Setting", R.drawable.ic_round_settings_24),
    Empty("", 0)
}
