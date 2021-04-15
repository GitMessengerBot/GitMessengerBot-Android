/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import me.sungbin.androidutils.util.Logger
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.theme.BindView
import me.sungbin.gitmessengerbot.theme.SystemUiController
import me.sungbin.gitmessengerbot.theme.colors
import me.sungbin.gitmessengerbot.ui.fancybottombar.FancyBottomBar
import me.sungbin.gitmessengerbot.ui.fancybottombar.FancyItem

/**
 * Created by Ji Sungbin on 2021/04/15.
 */

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setSystemBarsColor(colors.primary)
        val items = listOf(
            FancyItem("test", R.drawable.ic_baseline_folder_24, 0),
            FancyItem("", R.drawable.ic_baseline_error_24, 1),
            FancyItem("test2", R.drawable.ic_baseline_key_24, 2),
            FancyItem("", R.drawable.ic_baseline_battery_alert_24, 3)
        )

        setContent {
            BindView {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    FancyBottomBar(primaryColor = colors.primary, items = items) {
                        Logger.i(name, id)
                    }
                }
            }
        }
    }
}
