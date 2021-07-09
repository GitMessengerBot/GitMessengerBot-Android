/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ScriptViewModel.kt] created by Ji Sungbin on 21. 6. 19. 오후 11:22.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.script

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import me.sungbin.gitmessengerbot.util.Script
import me.sungbin.gitmessengerbot.util.Storage
import me.sungbin.gitmessengerbot.util.config.PathConfig

class ScriptViewModel : ViewModel() {
    private val _scripts = SnapshotStateList<ScriptItem>()
    private val compileStates: HashMap<Int, MutableState<Boolean>> = hashMapOf()

    val scripts: List<ScriptItem> get() = _scripts

    fun getCompileState(id: Int): State<Boolean> {
        if (compileStates[id] == null) {
            compileStates[id] = mutableStateOf(false)
        }
        return compileStates[id]!!
    }

    fun getScriptById(id: Int) = scripts.first { it.id == id }

    fun setCompileState(id: Int, state: Boolean) {
        compileStates[id] = mutableStateOf(state)
    }

    fun save(script: ScriptItem, code: String) {
        Storage.write(PathConfig.Script(script.name, script.lang), code)
    }

    fun loadCode(script: ScriptItem) =
        Storage.read(PathConfig.Script(script.name, script.lang), "")!!

    fun addScript(scriptItem: ScriptItem) {
        _scripts.add(scriptItem)
    }

    fun removeScript(scriptItem: ScriptItem) {
        _scripts.remove(scriptItem)
    }

    fun loadClassList(scriptItem: ScriptItem): List<Class> {
        // todo
        return listOf(
            Class(name = "test00", code = ""),
            Class(name = "test01", code = ""),
            Class(name = "test02", code = ""),
            Class(name = "test03", code = ""),
            Class(name = "test04", code = ""),
            Class(name = "test05", code = ""),
            Class(name = "test06", code = ""),
            Class(name = "test07", code = ""),
            Class(name = "test08", code = ""),
            Class(name = "test09", code = ""),
        )
    }

    init {
        _scripts.addAll(Script.getList())
    }
}
