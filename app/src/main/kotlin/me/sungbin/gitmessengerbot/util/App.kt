/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.util

import java.io.File

object App {
    fun isSetupDone() = File("${StorageUtil.sdcard}/${PathManager.Setting}/GithubData.json").exists()
}
