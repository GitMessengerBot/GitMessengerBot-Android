/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Bot.kt] created by Ji Sungbin on 21. 7. 10. 오전 11:31.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.bot.api

import android.content.Context
import io.github.jisungbin.gitmessengerbot.common.exception.CoreException
import io.github.sungbin.gitmessengerbot.core.bot.Bot
import io.github.sungbin.gitmessengerbot.core.bot.Sender
import io.github.sungbin.gitmessengerbot.core.bot.StackManager
import io.github.sungbin.gitmessengerbot.core.bot.debug.DebugStore
import io.github.sungbin.gitmessengerbot.core.bot.debug.createDebugItem

// TODO: context 메모리 누수 해결
internal class BotApi(private val context: Context, private val scriptId: Int) {
    private val showAll = "\u200b".repeat(500)

    private fun getReplySession(room: String) = StackManager.sessions[room]
        ?: throw CoreException("StackManager.sessions에 $scriptId 키가 없어요.")

    fun reply(room: String, message: String, isDebugMode: Boolean) {
        if (!isDebugMode) {
            Bot.replyToSession(
                context,
                getReplySession(room),
                message
            )
        } else {
            DebugStore.add(createDebugItem(scriptId, message, "null", Sender.Bot))
        }
    }

    fun replyShowAll(room: String, message: String, message2: String, isDebugMode: Boolean) {
        val content = "$message$showAll$message2"

        if (!isDebugMode) {
            Bot.replyToSession(
                context,
                getReplySession(room),
                content
            )
        } else {
            DebugStore.add(createDebugItem(scriptId, content, "null", Sender.Bot))
        }
    }
}
