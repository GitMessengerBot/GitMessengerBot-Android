/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [LiveData.kt] created by Ji Sungbin on 21. 8. 28. 오후 4:20
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.util.operator

import androidx.lifecycle.MutableLiveData

operator fun <T> MutableLiveData<List<T>>.plusAssign(item: T) {
    val value = this.value ?: emptyList()
    this.value = value + listOf(item)
}
