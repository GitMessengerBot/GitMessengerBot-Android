package com.sungbin.gitkakaobot.bot.v8

import android.content.Context
import com.sungbin.gitkakaobot.util.JsUtil


/**
 * Created by SungBin on 2020-12-28.
 */

class Api {

    companion object {
        private lateinit var context: Context

        fun init(context: Context) {
            this.context = context
        }
    }

    fun runRhino(source: String) = JsUtil.runRhino(source)
    fun getContext() = context

}