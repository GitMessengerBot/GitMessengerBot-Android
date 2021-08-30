/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [runif.kt] created by Ji Sungbin on 21. 7. 26. 오전 5:11.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.common.extension

inline fun <T> T.runIf(condition: Boolean, run: T.() -> T) = if (condition) {
    run()
} else this
