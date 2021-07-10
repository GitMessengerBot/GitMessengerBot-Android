/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [EditorActivity.kt] created by Ji Sungbin on 21. 7. 10. 오전 6:01.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.editor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import me.sungbin.gitmessengerbot.activity.main.script.ts2js.Ts2JsRepo
import me.sungbin.gitmessengerbot.bot.Bot
import me.sungbin.gitmessengerbot.theme.MaterialTheme
import me.sungbin.gitmessengerbot.theme.SystemUiController
import me.sungbin.gitmessengerbot.theme.colors
import me.sungbin.gitmessengerbot.util.config.PathConfig

@AndroidEntryPoint
class EditorActivity : ComponentActivity() {

    @Inject
    lateinit var ts2JsRepo: Ts2JsRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).run {
            setStatusBarColor(colors.primary)
            setNavigationBarColor(Color.White)
        }

        val scriptId = intent.getIntExtra(PathConfig.IntentScriptId, -1)

        setContent {
            MaterialTheme {
                Editor(script = Bot.getScriptById(scriptId), ts2JsRepo = ts2JsRepo)
            }
        }
    }
}
