/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [NotificationUtil.kt] created by Ji Sungbin on 21. 7. 10. 오전 10:52.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.DrawableRes
import me.sungbin.gitmessengerbot.activity.splash.SplashActivity

@Suppress("DEPRECATION")
object NotificationUtil {
    private fun builder(context: Context, channelId: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(context, channelId)
        } else Notification.Builder(context)

    fun createChannel(context: Context, name: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getManager(context).createNotificationChannelGroup(
                NotificationChannelGroup(
                    name,
                    name
                )
            )

            val channelMessage =
                NotificationChannel(name, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                    this.description = description
                    group = name
                    enableVibration(false)
                }
            getManager(context).createNotificationChannel(channelMessage)
        }
    }

    private fun pendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, SplashActivity::class.java)
        return PendingIntent.getActivity(context, 500, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    private fun getManager(context: Context) =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNormalNotification(
        context: Context,
        id: Int,
        channelId: String,
        title: String,
        content: String,
        icon: Int,
        isOnGoing: Boolean
    ) {
        getManager(context).notify(
            id,
            getNormalNotification(context, channelId, title, content, icon, isOnGoing).build()
        )
    }

    fun showInboxStyleNotification(
        context: Context,
        id: Int,
        channelId: String,
        title: String,
        content: String,
        boxText: List<String>,
        @DrawableRes icon: Int,
        isOnGoing: Boolean
    ) {
        val builder = builder(context, channelId)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(icon)
            .setAutoCancel(true)
            .setOngoing(isOnGoing)
            .setContentIntent(pendingIntent(context))

        val inboxStyle = Notification.InboxStyle()
        inboxStyle.setBigContentTitle(title)
        inboxStyle.setSummaryText(content)

        for (element in boxText) {
            inboxStyle.addLine(element)
        }

        builder.style = inboxStyle

        getManager(context).notify(id, builder.build())
    }

    fun getNormalNotification(
        context: Context,
        channelId: String,
        title: String,
        content: String,
        @DrawableRes icon: Int,
        isOnGoing: Boolean
    ) = builder(context, channelId)
        .setContentTitle(title)
        .setContentText(content)
        .setSmallIcon(icon)
        .setAutoCancel(true)
        .setOngoing(isOnGoing)
        .setContentIntent(pendingIntent(context))
}
