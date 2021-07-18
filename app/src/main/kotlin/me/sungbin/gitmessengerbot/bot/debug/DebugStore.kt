/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [DebugStore.kt] created by Ji Sungbin on 21. 7. 19. 오전 1:09.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.bot.debug

import androidx.compose.runtime.mutableStateListOf
import me.sungbin.gitmessengerbot.util.Json
import me.sungbin.gitmessengerbot.util.Storage
import me.sungbin.gitmessengerbot.util.config.StringConfig

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

    fun getByScriptId(scriptId: Int) = items.filter { it.scriptId == scriptId }

    fun add(item: DebugItem) {
        _items.add(item)
        Storage.write(StringConfig.Debug(item.scriptId), Json.toString(item))
    }

    fun removeAll(scriptId: Int) {
        _items.removeAll { it.scriptId == scriptId }
        Storage.deleteAll(StringConfig.Debug(scriptId))
    }
}
