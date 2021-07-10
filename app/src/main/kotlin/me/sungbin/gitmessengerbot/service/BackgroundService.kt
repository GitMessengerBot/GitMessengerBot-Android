/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [BackgroundService.kt] created by Ji Sungbin on 21. 7. 10. 오전 10:51.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.PowerManager
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.util.NotificationUtil

class BackgroundService : Service() {
    private val pm by lazy { getSystemService(Context.POWER_SERVICE) as PowerManager }
    private val wakeLock by lazy { pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, wakeLockLabel) }
    private val wakeLockLabel by lazy { getString(R.string.app_name) } // Context.getString() -> by lazy
    private val notificationId = 1000
    private val notification by lazy {
        NotificationUtil.getNormalNotification(
            context = applicationContext,
            channelId = getString(R.string.notification_channel_name),
            title = getString(R.string.app_name),
            content = getString(R.string.service_notification_bot_running),
            icon = R.drawable.ic_round_logo_512,
            isOnGoing = true
        )
    }

    override fun onBind(intent: Intent?): IBinder? = null

    @SuppressLint("WakelockTimeout")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(notificationId, notification.build())
        wakeLock.acquire()
        return START_STICKY
    }

    override fun onDestroy() {
        stopForeground(true)
        wakeLock.release()
    }
}
