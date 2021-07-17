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

fun createDebugItem(scriptId: Int, message: String, profileImageBase64: String, sender: String) =
    DebugItem(
        scriptId = scriptId,
        message = message,
        time = Date().time,
        profileImageBase64 = profileImageBase64,
        sender = sender
    )

data class DebugItem(
    val scriptId: Int,
    val message: String,
    val time: Long,
    val profileImageBase64: String,
    val sender: String
)

object Sender {
    const val Bot = "Bot"
}

@Suppress("ObjectPropertyName")
object DebugStore {
    private val _items = mutableStateListOf<DebugItem>()

    val items get() = _items.sortedByDescending { it.time }.asReversed()

    init {
        _items.addAll(getList())
    }

    private fun getList(): List<DebugItem> {
        val debugs = mutableListOf<DebugItem>()
        Storage.fileList(StringConfig.DebugPath()).forEach { debugFolder ->
            Storage.fileList(debugFolder.path).forEach { debugFile ->
                debugs.add(Json.toModel(Storage.read(debugFile.path, null)!!, DebugItem::class))
            }
        }
        return debugs
    }

    fun add(item: DebugItem) {
        _items.add(item)
        Storage.write(StringConfig.Debug(item.scriptId), Json.toString(item))
    }

    fun removeAll(scriptId: Int) {
        _items.removeAll { it.scriptId == scriptId }
        Storage.deleteAll(StringConfig.Debug(scriptId))
    }
}
