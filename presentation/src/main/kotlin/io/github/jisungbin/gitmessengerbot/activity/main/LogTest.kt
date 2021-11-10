/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [LogTest.kt] created by Ji Sungbin on 21. 11. 10. 오후 9:57
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.main

import android.os.Build
import java.util.regex.Pattern

private const val MAX_LOG_LENGTH = 4000
private const val MAX_TAG_LENGTH = 23
private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")

inline val tag get() = Throwable().stackTrace.first().let(::createStackElementTag)

@PublishedApi
internal fun createStackElementTag(element: StackTraceElement): String {
    var tag = element.className.substringAfterLast('.')
    val m = ANONYMOUS_CLASS.matcher(tag)
    if (m.find()) {
        tag = m.replaceAll("")
    }
    return if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= 26) {
        tag
    } else {
        tag.substring(0, MAX_TAG_LENGTH)
    }
}
