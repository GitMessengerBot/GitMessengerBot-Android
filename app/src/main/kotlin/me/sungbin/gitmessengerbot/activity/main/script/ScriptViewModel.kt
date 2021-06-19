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
import androidx.lifecycle.ViewModel

class ScriptViewModel private constructor() : ViewModel() {
    private val compileStates: HashMap<Int, MutableState<Boolean>> = hashMapOf()

    fun getCompileState(id: Int): State<Boolean> {
        var state = compileStates[id]
        if (state == null) {
            state = mutableStateOf(false)
            compileStates[id] = state
        }
        return state
    }

    fun setCompileState(id: Int, state: Boolean) {
        compileStates[id] = mutableStateOf(state)
    }

    fun saveInstance() {
        // todo
    }

    companion object {
        val instance by lazy { ScriptViewModel() }
    }
}
