/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [MessageService.kt] created by Ji Sungbin on 21. 7. 10. 오전 11:05.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.service

import android.app.Notification
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.text.Spanned
import androidx.core.content.pm.PackageInfoCompat
import androidx.core.text.HtmlCompat
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.bot.Bot
import me.sungbin.gitmessengerbot.bot.StackManager.sessions
import me.sungbin.gitmessengerbot.util.Util
import me.sungbin.gitmessengerbot.util.extension.toast

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
        if (sbn.packageName != "com.kakao.talk") return
        // todo: 커스텀 패키지, power 추가
        val wExt = Notification.WearableExtender(sbn.notification)
        for (act in wExt.actions) {
            if (act.remoteInputs.isNotEmpty()) {
                if (act.title.toString().lowercase().contains("reply") ||
                    act.title.toString().contains("답장")
                ) {
                    val extras = sbn.notification.extras
                    var room: String?
                    var sender: String?
                    var msg: String?
                    var isGroupChat = false
                    val packageName = sbn.packageName

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        room = extras.getString("android.summaryText")
                        sender = extras.get("android.title")?.toString()
                        msg = extras.get("android.text")?.toString()
                        if (room == null) {
                            room = sender
                            isGroupChat = false
                        } else isGroupChat = true
                    } else {
                        var kakaotalkVersion = 0L
                        var noKakaoTalk = false
                        try {
                            kakaotalkVersion =
                                PackageInfoCompat.getLongVersionCode(
                                    packageManager.getPackageInfo("com.kakao.talk", 0)
                                )
                        } catch (ignored: Exception) {
                            noKakaoTalk = true
                        }

                        if (noKakaoTalk || packageName != "com.kakao.talk" ||
                            kakaotalkVersion < 1907310
                        ) {
                            room = extras.getString("android.title")
                            if (extras.get("android.text") !is String) {
                                val html = HtmlCompat.toHtml(
                                    extras.get("android.text") as Spanned,
                                    HtmlCompat.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE
                                )
                                sender = HtmlCompat.fromHtml(
                                    html.split("<b>")[1].split("</b>")[0],
                                    HtmlCompat.FROM_HTML_MODE_COMPACT
                                ).toString()
                                msg = HtmlCompat.fromHtml(
                                    html.split("</b>")[1].split("</p>")[0]
                                        .substring(1),
                                    HtmlCompat.FROM_HTML_MODE_COMPACT
                                ).toString()
                            } else {
                                sender = room
                                msg = extras.get("android.text")?.toString()
                            }
                        } else {
                            room = extras.getString("android.subText")
                            sender = extras.getString("android.title")
                            msg = extras.getString("android.text")
                            isGroupChat = room != null
                            if (room == null) room = sender
                        }
                    }

                    if (!sessions.containsKey(room)) sessions[room!!] = act
                    /* if (!PictureManager.profileImage.containsKey(room)) PictureManager.profileImage[sender] =
                         sbn.notification.getLargeIcon().toBitmap(context)*/ // todo

                    chatHook(room!!, msg!!, sender!!, isGroupChat)
                }
            }
        }
    }

    private fun chatHook(
        room: String,
        message: String,
        sender: String,
        isGroupChat: Boolean
    ) {
        try {
            for (script in Bot.scripts) {
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
            Util.error(applicationContext, exception)
        }
    }
}
