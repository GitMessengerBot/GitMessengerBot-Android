package com.sungbin.gitkakaobot.bot.v8

import com.sungbin.gitkakaobot.bot.Bot
import com.sungbin.gitkakaobot.util.manager.StackManager


/**
 * Created by SungBin on 2020-12-22.
 */

class V8Replier {

    fun reply(room: String, message: String) {
        Bot.replyToSession(StackManager.sessions[room]!!, message)
    }

}