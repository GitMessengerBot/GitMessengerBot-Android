/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Result.kt] created by Ji Sungbin on 21. 8. 28. 오후 5:05
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.util.repo

sealed class RequestResult<out T> {
    data class Success<out T>(val response: T) : RequestResult<T>()
    data class Fail(val exception: Exception) : RequestResult<kotlin.Nothing>()
}
