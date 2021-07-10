/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Util.kt] created by Ji Sungbin on 21. 7. 10. 오후 12:31.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.util

import android.content.Context
import me.sungbin.gitmessengerbot.util.extension.toast

object Util {
    fun error(context: Context, exception: Exception) {
        val message = exception.message!!
        toast(context, message)
        println("Error: $message")
    }
}
