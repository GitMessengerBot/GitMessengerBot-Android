/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [NotificationUtil.kt] created by Ji Sungbin on 21. 8. 28. 오후 2:33.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.util

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat

@Suppress("DEPRECATION")
object NotificationUtil {
    private fun builder(context: Context, channelId: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(context, channelId)
        } else NotificationCompat.Builder(context)

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

    fun requestReadPermission(activity: Activity) {
        val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
        activity.startActivity(intent)
    }

    private fun getActivityPendingIntent(activity: Activity): PendingIntent {
        val intent = Intent(activity, activity.javaClass)
        return PendingIntent.getActivity(activity, 500, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    private fun getManager(context: Context) =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNormalNotification(
        contentActivity: Activity,
        id: Int,
        channelId: String,
        title: String,
        content: String,
        icon: Int,
        isOnGoing: Boolean,
        showTimestamp: Boolean,
    ) {
        getManager(contentActivity.applicationContext).notify(
            id,
            getNormalNotification(
                contentActivity,
                channelId,
                title,
                content,
                icon,
                isOnGoing,
                showTimestamp
            ).build()
        )
    }

    fun getNormalNotification(
        contentActivity: Activity,
        channelId: String,
        title: String,
        content: String,
        @DrawableRes icon: Int,
        isOnGoing: Boolean,
        showTimestamp: Boolean,
    ) = builder(contentActivity.applicationContext, channelId)
        .setContentTitle(title)
        .setContentText(content)
        .setSmallIcon(icon)
        .setAutoCancel(true)
        .setOngoing(isOnGoing)
        .setContentIntent(getActivityPendingIntent(contentActivity))
        .setWhen(System.currentTimeMillis())
        .setShowWhen(showTimestamp)
}
