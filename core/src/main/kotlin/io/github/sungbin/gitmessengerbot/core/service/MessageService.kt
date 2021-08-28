/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [MessageService.kt] created by Ji Sungbin on 21. 7. 10. 오전 11:05.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.service

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.util.Util
import io.github.jisungbin.gitmessengerbot.util.extension.toast
import io.github.sungbin.gitmessengerbot.core.bot.Bot
import io.github.sungbin.gitmessengerbot.core.bot.StackManager.sessions

class MessageService : NotificationListenerService() {

    override fun onCreate() {
        super.onCreate()
        toast(applicationContext, getString(R.string.service_message_bot_start))
    }

    override fun onDestroy() {
        super.onDestroy()
        toast(applicationContext, getString(R.string.service_message_bot_stop))
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)
        try {
            if (!Bot.app.value.kakaoTalkPackageNames.contains(sbn.packageName)) return
            val wExt = Notification.WearableExtender(sbn.notification)
            for (action in wExt.actions) {
                if (action.remoteInputs.isNotEmpty()) {
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
                        } else isGroupChat = true

                        if (!sessions.containsKey(room)) sessions[room] = action
                        /* if (!PictureManager.profileImage.containsKey(room)) PictureManager.profileImage[sender] =
                         sbn.notification.getLargeIcon().toBitmap(context)*/ // todo

                        println(listOf(room, message, sender, isGroupChat))
                        chatHook(room, message, sender, isGroupChat)
                    }
                }
            }
        } catch (ignored: Exception) {
        }
    }

    private fun chatHook(
        room: String,
        message: String,
        sender: String,
        isGroupChat: Boolean
    ) {
        try {
            for (script in Bot.getCompiledScripts()) {
                Bot.callJsResponder(
                    context = applicationContext,
                    script = script,
                    room = room,
                    message = message,
                    sender = sender,
                    isGroupChat = isGroupChat,
                    isDebugMode = false
                )
            }
        } catch (exception: Exception) {
            Util.error(applicationContext, "chatHook 에러\n\n$exception")
        }
    }
}
