package com.sungbin.gitkakaobot.bot.v8

import com.sungbin.androidutils.util.Logger


/**
 * Created by SungBin on 2020-12-22.
 */

class V8Log {

    fun log(message: String) {
        Logger.w("V8 Logger", message)
    }

}