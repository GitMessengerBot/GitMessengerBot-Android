package com.sungbin.gitkakaobot.bot.rhino

import android.app.Notification
import com.sungbin.gitkakaobot.bot.Bot.replyToSession
import com.sungbin.gitkakaobot.util.BotUtil.showAll
import com.sungbin.gitkakaobot.util.manager.StackManager.sessions

class Replier(
    private val session: Notification.Action?
) {
    fun reply(message: String) {
        replyToSession(session, message)
    }

    fun reply(room: String, message: String) {
        replyToSession(sessions[room], message)
    }

    fun replyShowAll(message: String, message2: String) {
        replyToSession(session, "$message$showAll$message2")
    }

    fun replyShowAll(room: String, message: String, message2: String) {
        replyToSession(sessions[room], "$message$showAll$message2")
    }
}