/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Wear.kt] created by Ji Sungbin on 21. 8. 30. 오후 5:05
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.common.core

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

object Wear {
    private const val WearOsPackage = "com.google.android.wearable.app"

    fun checkInstalled(context: Context): Boolean {
        val result = context.packageManager.getLaunchIntentForPackage(WearOsPackage)
        return result != null
    }

    fun install(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = "market://details?id=$WearOsPackage".toUri()
        }
        context.startActivity(intent)
    }
}
