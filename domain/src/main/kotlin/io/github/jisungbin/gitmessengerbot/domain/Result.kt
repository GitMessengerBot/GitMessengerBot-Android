/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Result.kt] created by Ji Sungbin on 21. 8. 13. 오후 7:41.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Result.kt] created by Ji Sungbin on 21. 7. 23. 오후 11:51.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain

sealed class Result<out T> {
    data class Success<out T>(val response: T) : Result<T>()
    data class Fail(val exception: Exception) : Result<Nothing>()
}

suspend fun <T> Result<T>.doWhen(
    onSuccess: suspend (response: T) -> Unit,
    onFail: suspend (exception: Exception) -> Unit
) {
    when (this) {
        is Result.Success -> onSuccess(response)
        is Result.Fail -> onFail(exception)
    }
}
