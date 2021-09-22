/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubResult.kt] created by Ji Sungbin on 21. 8. 28. 오후 11:18
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github

sealed class GithubResult<out T> {
    data class Success<out T>(val response: T) : GithubResult<T>()
    data class Fail(val exception: Exception) : GithubResult<Nothing>()
}

inline fun <T> GithubResult<T>.doWhen(
    onSuccess: (T) -> Unit,
    onFail: (Exception) -> Unit,
) {
    when (this) {
        is GithubResult.Success -> onSuccess(response)
        is GithubResult.Fail -> onFail(exception)
    }
}
