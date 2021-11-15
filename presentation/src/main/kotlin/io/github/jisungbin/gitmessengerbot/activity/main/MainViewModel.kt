/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [MainViewModel.kt] created by Ji Sungbin on 21. 11. 15. 오후 12:16
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _dashboardState = MutableStateFlow(Tab.Script)
    val dashboardState = _dashboardState.asStateFlow()

    @Suppress("MoveLambdaOutsideParentheses")
    private val _fabAction = MutableStateFlow({})
    val fabAction = _fabAction.asStateFlow()

    fun updateDashboardState(tab: Tab) {
        _dashboardState.value = tab
    }

    fun updateFabAction(action: () -> Unit) {
        _fabAction.value = action
    }
}
