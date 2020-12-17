package com.sungbin.gitkakaobot.listener

import android.app.Notification
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.text.Spanned
import androidx.core.content.pm.PackageInfoCompat
import androidx.core.text.HtmlCompat
import com.sungbin.androidutils.extensions.toBitmap
import com.sungbin.androidutils.util.DataUtil
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.util.BotUtil.botItems
import com.sungbin.gitkakaobot.util.UiUtil
import com.sungbin.gitkakaobot.util.manager.PathManager
import com.sungbin.gitkakaobot.util.manager.StackManager.sessions
import java.util.*


/**
 * Created by SungBin on 2020-08-22.
 */

class MessageListener : NotificationListenerService() {

    private lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        UiUtil.toast(applicationContext, getString(R.string.messagelistener_power_on))
    }

    override fun onDestroy() {
        super.onDestroy()
        UiUtil.toast(context, getString(R.string.messagelistener_power_off))
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)
        if (!DataUtil.readData(applicationContext, PathManager.POWER, "false").toBoolean()
        // || sbn.packageName != "com.kakao.talk" todo: 커스텀 패키지 추가
        ) return
        val wExt = Notification.WearableExtender(sbn.notification)
        for (act in wExt.actions) {
            if (act.remoteInputs.isNotEmpty()) {
                if (act.title.toString().toLowerCase(Locale.KOREA).contains("reply")
                    || act.title.toString().contains("답장")
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

                        if (noKakaoTalk || packageName != "com.kakao.talk"
                            || kakaotalkVersion < 1907310
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
                                        .substring(1), HtmlCompat.FROM_HTML_MODE_COMPACT
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

                    chatHook(
                        room!!, msg!!, sender!!, isGroupChat, act,
                        sbn.notification.getLargeIcon().toBitmap(context),
                        sbn.packageName
                    )
                }
            }
        }
    }

    private fun chatHook(
        room: String,
        msg: String,
        sender: String,
        isGroupChat: Boolean,
        session: Notification.Action?,
        profileImage: Bitmap?,
        packageName: String,
    ) {
        try {
            for (bot in botItems) {
                com.sungbin.gitkakaobot.bot.Bot.callJsResponder(
                    bot,
                    room,
                    msg,
                    sender,
                    isGroupChat,
                    session,
                    profileImage,
                    packageName,
                    false
                )
            }
        } catch (exception: Exception) {
            UiUtil.error(context, exception)
        }
    }

}