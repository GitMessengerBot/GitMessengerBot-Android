/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Bot.kt] created by Ji Sungbin on 21. 7. 10. 오전 11:31.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.bot.api

import android.content.Context
import me.sungbin.gitmessengerbot.bot.Bot
import me.sungbin.gitmessengerbot.bot.StackManager

class BotApi(private val context: Context) {
    private val showAll = "\u200b".repeat(500)

    fun reply(room: String, message: String) {
        Bot.replyToSession(context, StackManager.sessions[room]!!, message)
    }

    fun replyShowAll(room: String, message: String, message2: String) {
        Bot.replyToSession(context, StackManager.sessions[room]!!, "$message$showAll$message2")
    }
}
