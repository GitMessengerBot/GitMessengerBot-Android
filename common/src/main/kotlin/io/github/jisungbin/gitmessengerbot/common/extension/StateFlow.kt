/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [StateFlow.kt] created by Ji Sungbin on 21. 8. 30. 오후 5:06
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.common.extension

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> MutableStateFlow<List<T>>.clear() {
    value = emptyList()
}

fun <T> MutableStateFlow<List<T>>.removeAll(predicate: (T) -> Boolean) {
    val value = this.value.toMutableList()
    value.removeAll(predicate)
    notifyDataSetChanged(value)
}

fun <T> MutableStateFlow<List<T>>.removeIf(predicate: (T) -> Boolean) {
    val value = this.value.toMutableList()
    value.removeIf(predicate)
    notifyDataSetChanged(value)
}

@PublishedApi
internal fun <T> MutableStateFlow<List<T>>.notifyDataSetChanged(value: List<T>) {
    this.value = value
}

inline fun <T> MutableStateFlow<List<T>>.edit(action: MutableList<T>.() -> Unit) {
    val items = value.toMutableList()
    action(items)
    notifyDataSetChanged(items)
}
