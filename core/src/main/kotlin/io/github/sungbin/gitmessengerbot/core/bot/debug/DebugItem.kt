/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [DebugItem.kt] created by Ji Sungbin on 21. 7. 18. 오전 2:35.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.bot.debug

import java.util.Date

data class DebugItem(
    val scriptId: Int,
    val message: String,
    val time: Long,
    val profileImageBase64: String,
    val sender: String,
)

fun createDebugItem(scriptId: Int, message: String, profileImageBase64: String, sender: String) =
    DebugItem(
        scriptId = scriptId,
        message = message,
        time = Date().time,
        profileImageBase64 = profileImageBase64,
        sender = sender
    )

object Sender {
    const val Bot = "Bot"
}

fun List<DebugItem>.sortedByTime() = sortedByDescending { it.time }.asReversed()

fun List<DebugItem>.getByScriptId(scriptId: Int) = filter { it.scriptId == scriptId }
