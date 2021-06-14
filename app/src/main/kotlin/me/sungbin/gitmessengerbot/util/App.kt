/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [App.kt] created by Ji Sungbin on 21. 5. 21. 오후 4:39.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.util

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object App {
    val Build = SimpleDateFormat("HHmmss", Locale.KOREA).format(Date()).toString()

    fun isSetupDone(context: Context) =
        Storage.read(context, PathManager.Storage.GithubData, null) != null
}
