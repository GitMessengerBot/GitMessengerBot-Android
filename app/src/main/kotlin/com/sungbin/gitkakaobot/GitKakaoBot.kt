package com.sungbin.gitkakaobot

import android.app.Application
import com.sungbin.androidutils.util.NotificationUtil
import com.sungbin.gitkakaobot.bot.ApiClass
import com.sungbin.gitkakaobot.bot.Bot
import com.sungbin.gitkakaobot.util.BotUtil
import dagger.hilt.android.HiltAndroidApp


/**
 * Created by SungBin on 2020-08-23.
 */

@HiltAndroidApp
class GitKakaoBot : Application() {

    override fun onCreate() {
        super.onCreate()

        Bot.init(applicationContext)
        ApiClass.init(applicationContext)
        BotUtil.init(applicationContext)

        NotificationUtil.createChannel(
            applicationContext,
            getString(R.string.app_name),
            getString(R.string.foregroundservice_running_bot)
        )
    }

}