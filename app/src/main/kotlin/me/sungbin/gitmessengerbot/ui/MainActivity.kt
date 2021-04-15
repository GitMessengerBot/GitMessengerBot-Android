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
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.theme.BindView
import me.sungbin.gitmessengerbot.theme.SystemUiController
import me.sungbin.gitmessengerbot.theme.colors
import me.sungbin.gitmessengerbot.theme.defaultFontFamily
import me.sungbin.gitmessengerbot.ui.fancybottombar.FancyBottomBar
import me.sungbin.gitmessengerbot.ui.fancybottombar.FancyColors
import me.sungbin.gitmessengerbot.ui.fancybottombar.FancyItem
import me.sungbin.gitmessengerbot.ui.fancybottombar.FancyOptions

/**
 * Created by Ji Sungbin on 2021/04/15.
 */

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setSystemBarsColor(colors.primary)
        val items = listOf(
            FancyItem("Folders", R.drawable.ic_baseline_folder_24, 0),
            FancyItem(icon = R.drawable.ic_baseline_error_24, id = 1),
            FancyItem(title = "Keys", icon = R.drawable.ic_baseline_key_24, id = 2),
            FancyItem("More?", id = 3)
        )

        val fancyNavigationState = mutableStateOf(0)

        setContent {
            BindView {
                Box(modifier = Modifier.fillMaxSize()) {
                    Crossfade(
                        fancyNavigationState.value,
                        modifier = Modifier.fillMaxSize()
                    ) { index ->
                        BindFancyPage(index = index)
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        FancyBottomBar(
                            fancyColors = FancyColors(primary = colors.primary),
                            fancyOptions = FancyOptions(fontFamily = defaultFontFamily),
                            items = items
                        ) {
                            fancyNavigationState.value = id
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun BindFancyPage(index: Int) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "\uD83C\uDF1F Awesome FancyBottomBar \uD83C\uDF1F\nPage index: $index",
                fontSize = 20.sp,
                fontFamily = defaultFontFamily,
                textAlign = TextAlign.Center
            )
        }
    }
}
