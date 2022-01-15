/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [StateFlow.kt] created by Ji Sungbin on 21. 8. 30. 오후 5:06
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.common.operator

import kotlinx.coroutines.flow.MutableStateFlow

operator fun <T> MutableStateFlow<List<T>>.plusAssign(item: T) {
    this.value = this.value + listOf(item)
}

operator fun <T> MutableStateFlow<List<T>>.minusAssign(item: T) {
    this.value = this.value - listOf(item)
}
