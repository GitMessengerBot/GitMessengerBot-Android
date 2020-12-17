package com.sungbin.gitkakaobot

import android.app.Application
import com.sungbin.gitkakaobot.util.BotUtil
import dagger.hilt.android.HiltAndroidApp


/**
 * Created by SungBin on 2020-08-23.
 */

@HiltAndroidApp
class GitKakaoBot : Application() {

    override fun onCreate() {
        super.onCreate()

        BotUtil.init(applicationContext)
    }

}