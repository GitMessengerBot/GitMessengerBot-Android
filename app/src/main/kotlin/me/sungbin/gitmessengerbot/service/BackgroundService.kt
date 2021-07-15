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
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.util.NotificationUtil
import me.sungbin.gitmessengerbot.util.config.StringConfig

class BackgroundService : Service() {
    private val pm by lazy { getSystemService(Context.POWER_SERVICE) as PowerManager }
    private val wakeLock by lazy { pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, wakeLockLabel) }
    private val wakeLockLabel by lazy { getString(R.string.app_name) } // Context.getString() -> by lazy

    override fun onBind(intent: Intent?): IBinder? = null

    @SuppressLint("WakelockTimeout")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notificationId = 1000
        val botTogglePendingIntentRequestCode = 1001
        val botRecompilePendingIntentRequestCode = 1002

        var notification = NotificationUtil.getNormalNotification(
            context = applicationContext,
            channelId = getString(R.string.notification_channel_name),
            title = getString(R.string.app_name),
            content = getString(R.string.service_notification_bot_running),
            icon = R.drawable.ic_round_logo_512,
            isOnGoing = true,
            showTimestamp = true
        )

        val botPowerToggleIntent = Intent(this, NotifiactionService::class.java).apply {
            putExtra(StringConfig.IntentNotificationAction, StringConfig.IntentBotPowerToggle)
        }

        val botPowerTogglePendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            botTogglePendingIntentRequestCode,
            botPowerToggleIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        notification = notification.addAction(
            NotificationCompat.Action.Builder(
                0,
                "봇 전원 On/Off",
                botPowerTogglePendingIntent
            ).build()
        )

        val botRecompileIntent = Intent(this, NotifiactionService::class.java).apply {
            putExtra(StringConfig.IntentNotificationAction, StringConfig.IntentBotRecompile)
        }

        val botRecompilePendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            botRecompilePendingIntentRequestCode,
            botRecompileIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        notification = notification.addAction(
            NotificationCompat.Action.Builder(
                0,
                "봇 전체 스크립트 리컴파일",
                botRecompilePendingIntent
            ).build()
        )

        startForeground(notificationId, notification.build())
        wakeLock.acquire()
        return START_STICKY
    }

    override fun onDestroy() {
        stopForeground(true)
        wakeLock.release()
    }
}
