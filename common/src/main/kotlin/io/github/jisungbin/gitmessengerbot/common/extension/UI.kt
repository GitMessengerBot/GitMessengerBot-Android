/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [UI.kt] created by Ji Sungbin on 21. 8. 30. 오후 5:06
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.common.extension

import android.app.Activity
import android.content.Context
import android.widget.Toast

fun toast(activity: Activity, message: String, length: Int = Toast.LENGTH_SHORT) {
    activity.runOnUiThread {
        Toast.makeText(activity, message, length).show()
    }
}

fun toast(context: Context, message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, length).show()
}