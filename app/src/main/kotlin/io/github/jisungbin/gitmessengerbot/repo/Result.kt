/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Result.kt] created by Ji Sungbin on 21. 7. 23. 오후 11:51.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.repo

sealed class Result {
    data class Success<T>(val response: T) : Result()
    data class Fail(val exception: Exception) : Result()
}
