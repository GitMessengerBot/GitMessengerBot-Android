/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Result.kt] created by Ji Sungbin on 21. 11. 7. 오후 8:33
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.common.extension

inline fun <T> Result<T>.doWhen(
    onSuccess: (result: T) -> Unit,
    onFailure: (throwable: Throwable) -> Unit
) {
    if (isSuccess) {
        onSuccess(getOrNull()!!)
    } else {
        onFailure(exceptionOrNull()!!)
    }
}
