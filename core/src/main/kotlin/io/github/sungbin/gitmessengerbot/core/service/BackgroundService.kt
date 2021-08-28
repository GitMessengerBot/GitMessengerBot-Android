/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [BackgroundService.kt] created by Ji Sungbin on 21. 7. 10. 오전 10:51.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.service

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.util.NotificationUtil
import io.github.jisungbin.gitmessengerbot.util.config.StringConfig

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
                getString(R.string.service_notification_action_bot_power_toggle),
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
                getString(R.string.service_notification_action_bot_all_recompile),
                botRecompilePendingIntent
            ).build()
        )

        startForeground(notificationId, notification.build())
        wakeLock.acquire()
        return START_STICKY
    }

    override fun onDestroy() {
        try {
            stopForeground(true)
            wakeLock.release()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }
}
