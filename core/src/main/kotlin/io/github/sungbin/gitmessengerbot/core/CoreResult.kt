/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [CoreResult.kt] created by Ji Sungbin on 21. 8. 28. 오후 11:18
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core

sealed class CoreResult<out T> {
    data class Success<out T>(val response: T) : CoreResult<T>()
    data class Fail(val exception: Exception) : CoreResult<Nothing>()
}

suspend fun <T> CoreResult<T>.doWhen(
    onSuccess: suspend (T) -> Unit,
    onFail: suspend (Exception) -> Unit,
) {
    when (this) {
        is CoreResult.Success -> onSuccess(response)
        is CoreResult.Fail -> onFail(exception)
    }
}
