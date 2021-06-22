/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [DebugViewModel.kt] created by Ji Sungbin on 21. 6. 19. 오후 11:45.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.debug

import androidx.lifecycle.ViewModel

class DebugViewModel private constructor() : ViewModel() {

    fun save() {
    }

    companion object {
        val instance by lazy { DebugViewModel() }
    }
}
