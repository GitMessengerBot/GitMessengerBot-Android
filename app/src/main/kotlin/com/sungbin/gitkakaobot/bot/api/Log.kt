package com.sungbin.gitkakaobot.bot.api

import com.sungbin.androidutils.util.Logger


/**
 * Created by SungBin on 2020-12-22.
 */

class Log {

    fun test(value: Any) {
        Logger.w("Log.test", value)
    }

    fun e(message: String) {
        // todo: error 로그 만들기
    }

    fun d(message: String) {
        // todo: debug 로그 만들기
    }

    fun i(message: String) {
        // todo: info 로그 만들기
    }

}