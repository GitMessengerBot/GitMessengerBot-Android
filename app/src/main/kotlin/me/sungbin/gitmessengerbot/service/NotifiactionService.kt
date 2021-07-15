/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [NotifiactionService.kt] created by Ji Sungbin on 21. 7. 16. 오전 1:55.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import me.sungbin.gitmessengerbot.util.config.StringConfig

class NotifiactionService : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.getStringExtra(StringConfig.IntentNotificationAction)) {
            StringConfig.IntentBotPowerToggle -> println("봇 파워 토글")
            StringConfig.IntentBotRecompile -> println("봇 리컴파일")
        }
    }
}
