/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [App.kt] created by Ji Sungbin on 21. 5. 21. 오후 4:39.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import me.sungbin.gitmessengerbot.util.config.PathConfig

object App {
    val BuildTime = SimpleDateFormat("HHmmss", Locale.KOREA).format(Date()).toString()

    fun isSetupDone() = Storage.read(PathConfig.GithubData, null) != null
}
