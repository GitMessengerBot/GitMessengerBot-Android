/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [LiveData.kt] created by Ji Sungbin on 21. 8. 28. 오후 4:38
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.util.extension

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<List<T>>.clear() {
    value = emptyList()
}

fun <T> MutableLiveData<List<T>>.removeAll(predicate: (T) -> Boolean) {
    val value = this.value?.toMutableList() ?: return
    value.removeAll(predicate)
    this.value = value
}
