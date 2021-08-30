/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Response.kt] created by Ji Sungbin on 21. 8. 30. 오후 4:08
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.util

import retrofit2.Response

fun <T> Response<T>.isValid() = isSuccessful && body() != null
