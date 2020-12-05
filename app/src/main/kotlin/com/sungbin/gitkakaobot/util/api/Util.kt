package com.sungbin.gitkakaobot.util.api

import android.content.Context
import android.os.Vibrator
import com.sungbin.gitkakaobot.R
import com.sungbin.sungbintool.NotificationUtils
import com.sungbin.sungbintool.Utils

@Suppress("DEPRECATION")
object Util {
    private lateinit var context: Context
    private lateinit var vibrator: Vibrator

    fun init(context: Context) {
        this.context = context
        vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    fun makeNoti(title: String, content: String) {
        NotificationUtils.setGroupName(context.getString(R.string.app_name))
        NotificationUtils.createChannel(
            context,
            context.getString(R.string.app_name), context.getString(R.string.app_name)
        )
        NotificationUtils.showNormalNotification(
            context,
            1, title, content, R.mipmap.ic_launcher
        )
    }

    fun makeVibration(ms: Int) {
        vibrator.vibrate(ms * 1000.toLong())
    }

    fun copy(content: String) {
        Utils.copy(context, content)
    }
}