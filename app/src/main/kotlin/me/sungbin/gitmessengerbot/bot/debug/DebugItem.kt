/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [DebugItem.kt] created by Ji Sungbin on 21. 7. 18. 오전 2:35.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.bot.debug

import androidx.compose.runtime.mutableStateListOf
import java.util.Date
import me.sungbin.gitmessengerbot.util.Json
import me.sungbin.gitmessengerbot.util.Storage
import me.sungbin.gitmessengerbot.util.config.StringConfig

data class DebugItem(
    val scriptId: Int,
    val message: String,
    val time: Long,
    val profileImageBase64: String
)

fun createDebugItem(scriptId: Int, message: String, profileImageBase64: String) = DebugItem(
    scriptId = scriptId,
    message = message,
    time = Date().time,
    profileImageBase64 = profileImageBase64
)

@Suppress("ObjectPropertyName")
object Debug {
    private val _items = mutableStateListOf<DebugItem>()

    val items get() = _items.sortedByDescending { it.time }.asReversed()

    fun add(item: DebugItem) {
        _items.add(item)
        Storage.write(StringConfig.Debug(item.scriptId), Json.toString(item))
    }

    fun removeAll(scriptId: Int) {
        _items.removeAll { it.scriptId == scriptId }
        Storage.deleteAll(StringConfig.Debug(scriptId))
    }
}
