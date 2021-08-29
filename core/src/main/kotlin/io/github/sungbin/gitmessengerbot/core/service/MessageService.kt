/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [MessageService.kt] created by Ji Sungbin on 21. 7. 10. 오전 11:05.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.core.app.NotificationCompat
import io.github.jisungbin.gitmessengerbot.util.exception.CoreException
import io.github.jisungbin.gitmessengerbot.util.extension.toast
import io.github.sungbin.gitmessengerbot.core.R
import io.github.sungbin.gitmessengerbot.core.bot.Bot
import io.github.sungbin.gitmessengerbot.core.bot.StackManager.sessions
import io.github.sungbin.gitmessengerbot.core.setting.AppConfig

class MessageService : NotificationListenerService() {

    override fun onCreate() {
        super.onCreate()
        toast(applicationContext, getString(R.string.service_message_toast_bot_start))
    }

    override fun onDestroy() {
        super.onDestroy()
        toast(applicationContext, getString(R.string.service_message_toast_bot_stop))
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)
        try {
            if (!AppConfig.appValue.kakaoTalkPackageNames.contains(sbn.packageName)) return
            val wExt = NotificationCompat.WearableExtender(sbn.notification)
            for (action in wExt.actions) {
                val remoteInputs = action.remoteInputs ?: return
                if (remoteInputs.isNotEmpty()) {
                    if (action.title.toString().lowercase().contains("reply") ||
                        action.title.toString().contains("답장")
                    ) {
                        val extras = sbn.notification.extras
                        var isGroupChat: Boolean

                        var room = extras.getString("android.summaryText")
                        val sender = extras.get("android.title").toString()
                        val message = extras.get("android.text").toString()

                        if (room == null) {
                            room = sender
                            isGroupChat = false
                        } else {
                            isGroupChat = true
                        }

                        if (!sessions.containsKey(room)) sessions[room] = action

                        // TODO
                        /* if (!PictureManager.profileImage.containsKey(room)) PictureManager.profileImage[sender] =
                         sbn.notification.getLargeIcon().toBitmap(context)*/

                        println(listOf(room, message, sender, isGroupChat))
                        chatHook(room, message, sender, isGroupChat)
                    }
                }
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    private fun chatHook(
        room: String,
        message: String,
        sender: String,
        isGroupChat: Boolean,
    ) {
        try {
            for (script in Bot.getCompiledScripts()) {
                Bot.callJsResponder(
                    script = script,
                    room = room,
                    message = message,
                    sender = sender,
                    isGroupChat = isGroupChat
                )
            }
        } catch (exception: Exception) {
            throw CoreException(exception.message)
        }
    }
}
