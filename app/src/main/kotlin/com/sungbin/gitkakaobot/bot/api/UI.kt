package com.sungbin.gitkakaobot.bot.api

import android.content.Context
import android.view.View
import android.widget.Toast
import com.sungbin.androidutils.util.NotificationUtil
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.util.UiUtil


/**
 * Created by SungBin on 2020-12-28.
 */

class UI {

    companion object {
        private lateinit var context: Context

        fun init(context: Context) {
            this.context = context
        }
    }

    fun toast(message: String) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    fun notification(title: String, message: String, id: Int) {
        NotificationUtil.showNormalNotification(
            context,
            id,
            title,
            message,
            R.mipmap.ic_launcher,
            false
        )
    }

    fun snackbar(view: View, message: String) {
        UiUtil.snackbar(view, message)
    }

}