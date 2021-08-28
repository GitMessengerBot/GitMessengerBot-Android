/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [DebugStore.kt] created by Ji Sungbin on 21. 7. 19. 오전 1:09.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.bot.debug

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.jisungbin.gitmessengerbot.util.core.Storage
import io.github.jisungbin.gitmessengerbot.util.config.ScriptConfig
import io.github.jisungbin.gitmessengerbot.util.extension.clear
import io.github.jisungbin.gitmessengerbot.util.extension.removeAll
import io.github.jisungbin.gitmessengerbot.util.extension.toJsonString
import io.github.jisungbin.gitmessengerbot.util.extension.toModel
import io.github.jisungbin.gitmessengerbot.util.operator.plusAssign

@Suppress("ObjectPropertyName")
object DebugStore {
    private val _items = MutableLiveData(getList())
    val items get(): LiveData<List<DebugItem>> = _items

    private fun getList(): List<DebugItem> {
        val debugs = mutableListOf<DebugItem>()
        Storage.fileList(ScriptConfig.DebugAllPath).forEach { debugFolder ->
            Storage.fileList(debugFolder.path).forEach { debugFile ->
                debugs.add(Storage.read(debugFile.path, null)!!.toModel())
            }
        }
        return debugs
    }

    fun add(item: DebugItem) {
        _items += item
        Storage.write(ScriptConfig.DebugDataPath(item.scriptId), item.toJsonString())
    }

    fun removeAll() {
        _items.clear()
        Storage.deleteAll(ScriptConfig.DebugAllPath)
    }

    fun removeAll(scriptId: Int) {
        if (scriptId == ScriptConfig.DebugAllBot) {
            _items.removeAll { it.scriptId != ScriptConfig.EvalId }
            Storage.fileList(ScriptConfig.DebugAllPath).forEach { debugFolder ->
                if (debugFolder.path != ScriptConfig.DebugDataPath(ScriptConfig.EvalId)) {
                    debugFolder.deleteRecursively()
                }
            }
        } else {
            _items.removeAll { it.scriptId == scriptId }
            Storage.deleteAll(ScriptConfig.DebugDataPath(scriptId))
        }
    }
}
