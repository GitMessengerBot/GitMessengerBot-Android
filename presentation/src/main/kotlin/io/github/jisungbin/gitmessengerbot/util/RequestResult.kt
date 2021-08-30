/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [RequestResult.kt] created by Ji Sungbin on 21. 8. 29. 오후 7:49
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.util

sealed class RequestResult<out T> {
    data class Success<out T>(val response: T) : RequestResult<T>()
    data class Fail(val exception: Exception) : RequestResult<Nothing>()
}

suspend fun <T> RequestResult<T>.doWhen(
    onSuccess: suspend (T) -> Unit,
    onFail: suspend (Exception) -> Unit,
) {
    when (this) {
        is RequestResult.Success -> onSuccess(response)
        is RequestResult.Fail -> onFail(exception)
    }
}
