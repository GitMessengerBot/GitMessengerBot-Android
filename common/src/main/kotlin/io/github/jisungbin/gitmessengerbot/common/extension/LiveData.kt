/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [LiveData.kt] created by Ji Sungbin on 21. 8. 28. 오후 4:38
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.common.extension

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<List<T>>.clear() {
    value = emptyList()
}

fun <T> MutableLiveData<List<T>>.removeAll(predicate: (T) -> Boolean) {
    val value = this.value?.toMutableList() ?: return
    value.removeAll(predicate)
    notifyDataSetChanged(value)
}

fun <T> MutableLiveData<List<T>>.removeIf(predicate: (T) -> Boolean) {
    val value = this.value?.toMutableList() ?: return
    value.removeIf(predicate)
    notifyDataSetChanged(value)
}

@PublishedApi
internal fun <T> MutableLiveData<List<T>>.notifyDataSetChanged(value: List<T>) {
    this.value = value
}

inline fun <T> MutableLiveData<List<T>>.edit(action: MutableList<T>.() -> Unit) {
    val items = value?.toMutableList() ?: return
    action(items)
    notifyDataSetChanged(items)
}
