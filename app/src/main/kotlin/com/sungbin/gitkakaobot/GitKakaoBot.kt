package com.sungbin.gitkakaobot

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp


/**
 * Created by SungBin on 2020-08-23.
 */

@HiltAndroidApp
class GitKakaoBot : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}