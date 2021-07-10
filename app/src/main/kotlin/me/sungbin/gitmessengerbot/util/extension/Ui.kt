/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Ui.kt] created by Ji Sungbin on 21. 6. 14. 오후 9:59.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.util.extension

import android.app.Activity
import android.content.Context
import android.widget.Toast

fun toast(activity: Activity, message: String, length: Int = Toast.LENGTH_SHORT) {
    activity.runOnUiThread {
        Toast.makeText(activity, message, length).show()
    }
}

fun toast(context: Context, message: String, length: Int = Toast.LENGTH_SHORT) {
    toast(context as Activity, message, length)
}
