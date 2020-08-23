package com.sungbin.gitkakaobot.listener

import android.app.Notification
import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.text.Spanned
import android.util.Base64.encodeToString
import androidx.core.content.pm.PackageInfoCompat
import androidx.core.text.HtmlCompat
import com.faendir.rhino_android.RhinoAndroidHelper
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.util.DataUtil
import com.sungbin.gitkakaobot.util.UiUtil
import com.sungbin.gitkakaobot.util.manager.PathManager
import com.sungbin.gitkakaobot.util.manager.StackManager.scopes
import com.sungbin.gitkakaobot.util.manager.StackManager.scripts
import com.sungbin.gitkakaobot.util.manager.StackManager.sessions
import org.mozilla.javascript.Function
import org.mozilla.javascript.ScriptableObject
import java.io.ByteArrayOutputStream
import java.util.*


/**
 * Created by SungBin on 2020-08-22.
 */

class KakaoTalkListener : NotificationListenerService() {

    private lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        UiUtil.toast(context, "Power ON")
        init(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        UiUtil.toast(context, "Power OFF")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)
        if (!DataUtil.read(applicationContext, PathManager.POWER, "false").toBoolean()
            || sbn.packageName != "com.kakao.talk"
        ) return
        val wExt = Notification.WearableExtender(sbn.notification)
        for (act in wExt.actions) {
            if (act.remoteInputs.isNotEmpty()) {
                if (act.title.toString().toLowerCase(Locale.getDefault()).contains("reply")
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
                        "script", room!!, msg!!, sender!!, isGroupChat, act,
                        ((sbn.notification.getLargeIcon())
                            .loadDrawable(context) as BitmapDrawable).bitmap,
                        sbn.packageName
                    )
                }
            }
        }
    }

    companion object {
        val showAll = "\u200b".repeat(500)
        private lateinit var context: Context

        fun init(context: Context) {
            this.context = context
        }

        class Replier(
            private val session: Notification.Action?
        ) {

            fun reply(message: String) {
                replyToSession(session, message)
            }

            fun reply(room: String, message: String) {
                replyToSession(sessions[room], message)
            }

            fun replyShowAll(message: String, message2: String) {
                replyToSession(session, "$message$showAll$message2")
            }

            fun replyShowAll(room: String, message: String, message2: String) {
                replyToSession(sessions[room], "$message$showAll$message2")
            }
        }

        class ImageDB(private val bitmap: Bitmap?) {
            fun getProfileImage(): String? {
                if (bitmap == null) return null
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                val bImage = baos.toByteArray()
                return encodeToString(bImage, 0)
            }
        }

        fun chatHook(
            name: String,
            room: String,
            msg: String,
            sender: String,
            isGroupChat: Boolean,
            session: Notification.Action?,
            profileImage: Bitmap?,
            packageName: String,
        ) {
            try {
                callJsResponder(
                    name,
                    room,
                    msg,
                    sender,
                    isGroupChat,
                    session,
                    profileImage,
                    packageName
                )
            } catch (e: Exception) {
                UiUtil.toast(context, e.message.toString())
            }
        }

        private fun callJsResponder(
            name: String,
            room: String,
            msg: String,
            sender: String,
            isGroupChat: Boolean,
            session: Notification.Action?,
            profileImage: Bitmap?,
            packageName: String
        ) {
            val parseContext = RhinoAndroidHelper().enterContext().apply {
                languageVersion = org.mozilla.javascript.Context.VERSION_ES6
                optimizationLevel = -1
            }
            val responder = scripts[name]
            val scope = scopes[name]
            try {
                if (responder == null || scope == null) {
                    org.mozilla.javascript.Context.exit()
                    UiUtil.toast(
                        context,
                        context.getString(R.string.reload_script_first).replace("{name}", name)
                    )
                } else {
                    responder.call(
                        parseContext,
                        scope,
                        scope,
                        arrayOf(
                            room, msg, sender, isGroupChat,
                            Replier(session),
                            ImageDB(profileImage), packageName
                        )
                    )
                }
                org.mozilla.javascript.Context.exit()
            } catch (e: Exception) {
                UiUtil.toast(context, e.message.toString())
            }
        }

        fun compileJavaScript(name: String, sourceCode: String): String {
            return try {
                val rhino = RhinoAndroidHelper().enterContext().apply {
                    languageVersion = org.mozilla.javascript.Context.VERSION_ES6
                    optimizationLevel = -1
                }

                val scope = rhino.initStandardObjects()
                ScriptableObject.putProperty(scope, "context", context)
                rhino.compileString(sourceCode, name, 1, null).exec(rhino, scope)

                val responder = scope["response", scope] as Function
                scripts[name] = responder
                scopes[name] = scope
                org.mozilla.javascript.Context.exit()
                "컴파일 성공"
            } catch (e: Exception) {
                e.message.toString()
            }
        }

        private fun replyToSession(session: Notification.Action?, value: String) {
            if (session == null) {
                UiUtil.toast(context, context.getString(R.string.cant_load_session))
            } else {
                val sendIntent = Intent()
                val msg = Bundle()
                for (inputable in session.remoteInputs) msg.putCharSequence(
                    inputable.resultKey,
                    value
                )
                RemoteInput.addResultsToIntent(session.remoteInputs, sendIntent, msg)
                try {
                    session.actionIntent.send(context, 0, sendIntent)
                } catch (e: Exception) {
                    UiUtil.toast(context, e.message.toString())
                }
            }
        }
    }

}