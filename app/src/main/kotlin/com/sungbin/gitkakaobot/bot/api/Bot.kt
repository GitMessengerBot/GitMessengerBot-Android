package com.sungbin.gitkakaobot.bot.api

import com.sungbin.gitkakaobot.bot.Bot
import com.sungbin.gitkakaobot.util.BotUtil
import com.sungbin.gitkakaobot.util.manager.StackManager


/**
 * Created by SungBin on 2020-12-22.
 */

class Bot {

    fun reply(room: String, message: String) {
        Bot.replyToSession(StackManager.sessions[room]!!, message)
    }

    fun replyShowAll(room: String, message: String, message2: String) {
        Bot.replyToSession(StackManager.sessions[room]!!, "$message${BotUtil.showAll}$message2")
    }

}