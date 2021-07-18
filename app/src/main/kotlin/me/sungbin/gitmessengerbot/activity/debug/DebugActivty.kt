/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [DebugActivty.kt] created by Ji Sungbin on 21. 7. 18. 오전 3:01.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.debug

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import me.sungbin.gitmessengerbot.bot.Bot
import me.sungbin.gitmessengerbot.bot.debug.Debug
import me.sungbin.gitmessengerbot.theme.MaterialTheme
import me.sungbin.gitmessengerbot.theme.SystemUiController
import me.sungbin.gitmessengerbot.theme.colors
import me.sungbin.gitmessengerbot.theme.twiceLightGray
import me.sungbin.gitmessengerbot.util.config.StringConfig

class DebugActivty : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scriptId = intent.getIntExtra(StringConfig.IntentDebugScriptId, -1)
        val script = Bot.getScriptById(scriptId)

        SystemUiController(window).run {
            setStatusBarColor(colors.primary)
            setNavigationBarColor(twiceLightGray)
        }
        setContent {
            MaterialTheme {
                Debug(activity = this, script = script)
            }
        }
    }
}
