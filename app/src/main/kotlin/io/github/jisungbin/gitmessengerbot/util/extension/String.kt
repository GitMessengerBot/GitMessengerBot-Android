/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [String.kt] created by Ji Sungbin on 21. 7. 16. 오후 3:57.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.util.extension

import android.util.Base64
import java.nio.charset.Charset

// https://stackoverflow.com/a/35603952/14299073
fun String.isEnglish(): Boolean {
    val asciiEncoder = Charset.forName("US-ASCII").newEncoder()
    val isoEncoder = Charset.forName("ISO-8859-1").newEncoder()
    return asciiEncoder.canEncode(this) || isoEncoder.canEncode(this)
}

fun String.toBase64() = Base64.encodeToString(toByteArray(), Base64.NO_WRAP)!!
