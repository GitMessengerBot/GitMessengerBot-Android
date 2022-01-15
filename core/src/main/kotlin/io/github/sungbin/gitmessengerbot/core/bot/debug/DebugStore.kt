/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [DebugStore.kt] created by Ji Sungbin on 21. 7. 19. 오전 1:09.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.bot.debug

import io.github.jisungbin.gitmessengerbot.common.constant.ScriptConstant
import io.github.jisungbin.gitmessengerbot.common.core.Storage
import io.github.jisungbin.gitmessengerbot.common.exception.CoreException
import io.github.jisungbin.gitmessengerbot.common.extension.clear
import io.github.jisungbin.gitmessengerbot.common.extension.removeAll
import io.github.jisungbin.gitmessengerbot.common.extension.toJsonString
import io.github.jisungbin.gitmessengerbot.common.extension.toModel
import io.github.jisungbin.gitmessengerbot.common.operator.plusAssign
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object DebugStore {
    private val _items = MutableStateFlow(getList())
    val items = _items.asStateFlow()

    fun getByScriptId(id: Int) = items.value.filter { id == id }

    fun add(item: DebugItem) {
        _items += item
        Storage.write(ScriptConstant.DebugDataPath(item.scriptId), item.toJsonString())
    }

    fun removeAll() {
        _items.clear()
        Storage.deleteAll(ScriptConstant.DebugAllPath)
    }

    fun removeAll(scriptId: Int) {
        if (scriptId == ScriptConstant.DebugAllBot) {
            _items.removeAll { it.scriptId != ScriptConstant.EvalId }
            Storage.fileList(ScriptConstant.DebugAllPath).forEach { debugFolder ->
                if (debugFolder.path != ScriptConstant.DebugDataPath(ScriptConstant.EvalId)) {
                    debugFolder.deleteRecursively()
                }
            }
        } else {
            _items.removeAll { it.scriptId == scriptId }
            Storage.deleteAll(ScriptConstant.DebugDataPath(scriptId))
        }
    }

    private fun getList(): List<DebugItem> {
        val debugs = mutableListOf<DebugItem>()
        Storage.fileList(ScriptConstant.DebugAllPath).forEach { debugFolder ->
            Storage.fileList(debugFolder.path).forEach { debugFile ->
                debugs.add(
                    Storage.read(debugFile.path, null)?.toModel()
                        ?: throw CoreException("$debugFile 파일이 없어요.")
                )
            }
        }
        return debugs
    }
}
