/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [SimEditorActivity.kt] created by Ji Sungbin on 21. 7. 12. 오후 9:13.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.editor.simple

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import me.sungbin.gitmessengerbot.theme.MaterialTheme
import me.sungbin.gitmessengerbot.theme.SystemUiController
import me.sungbin.gitmessengerbot.theme.colors

class SimpleEditorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).run {
            setStatusBarColor(colors.primary)
            setNavigationBarColor(Color.White)
        }

        setContent {
            MaterialTheme {
            }
        }
    }
}
