/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [log.kt] created by Ji Sungbin on 21. 8. 28. 오후 10:42
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.common.core

import io.github.jisungbin.gitmessengerbot.common.BuildConfig

val isDebug = BuildConfig.DEBUG

fun log(any: Any?) {
    if (isDebug) {
        println(any)
    }
}
