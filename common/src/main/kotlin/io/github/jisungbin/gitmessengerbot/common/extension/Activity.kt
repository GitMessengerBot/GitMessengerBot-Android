/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Activity.kt] created by Ji Sungbin on 21. 8. 29. 오후 6:34
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.common.extension

import android.app.Activity
import android.widget.Toast

fun Activity.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    toast(this, message, length)
}
