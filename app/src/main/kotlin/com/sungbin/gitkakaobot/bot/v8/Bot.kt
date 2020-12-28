package com.sungbin.gitkakaobot.bot.v8

import com.sungbin.gitkakaobot.bot.Bot
import com.sungbin.gitkakaobot.util.BotUtil
import com.sungbin.gitkakaobot.util.manager.StackManager


/**
 * Created by SungBin on 2020-12-22.
 */

class Bot {

    companion object {
        @JvmStatic
        fun reply(room: String, message: String) {
            Bot.replyToSession(StackManager.sessions[room]!!, message)
        }

        @JvmStatic
        fun replyShowAll(room: String, message: String, message2: String) {
            Bot.replyToSession(StackManager.sessions[room]!!, "$message${BotUtil.showAll}$message2")
        }
    }

}