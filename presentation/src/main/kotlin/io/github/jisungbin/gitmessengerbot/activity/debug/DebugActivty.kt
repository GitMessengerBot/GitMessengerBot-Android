/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [DebugActivty.kt] created by Ji Sungbin on 21. 7. 18. 오전 3:01.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.debug

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.github.jisungbin.gitmessengerbot.common.constant.IntentConstant
import io.github.jisungbin.gitmessengerbot.common.exception.PresentationException
import io.github.jisungbin.gitmessengerbot.theme.MaterialTheme
import io.github.jisungbin.gitmessengerbot.theme.SystemUiController
import io.github.jisungbin.gitmessengerbot.theme.colors
import io.github.jisungbin.gitmessengerbot.theme.twiceLightGray
import io.github.sungbin.gitmessengerbot.core.bot.Bot

class DebugActivty : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scriptId = intent.getIntExtra(IntentConstant.DebugScriptId, -1)
        val script = try {
            Bot.getAllScripts().first { it.id == scriptId }
        } catch (exception: Exception) {
            throw PresentationException("$scriptId 아이디를 가진 DebugItem 스크립트가 존재하지 않아요. (${exception.message})")
        }

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
