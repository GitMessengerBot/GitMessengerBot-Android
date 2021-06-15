/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [LoggingInterceptor.kt] created by Ji Sungbin on 21. 6. 15. 오후 10:40.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.util

import javax.inject.Inject
import okhttp3.logging.HttpLoggingInterceptor

class LoggingInterceptor @Inject constructor() {
    val instance by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}
