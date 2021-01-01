package com.sungbin.gitkakaobot.bot.api

import com.sungbin.gitkakaobot.GitMessengerBot
import com.sungbin.gitkakaobot.util.JsUtil


/**
 * Created by SungBin on 2020-12-28.
 */

class Api {

    fun runRhino(source: String) = JsUtil.runRhino(source)
    fun getContext() = GitMessengerBot.context

}