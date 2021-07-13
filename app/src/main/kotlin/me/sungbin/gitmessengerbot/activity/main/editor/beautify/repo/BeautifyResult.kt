/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [BeautifyResult.kt] created by Ji Sungbin on 21. 7. 13. 오전 2:33.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.editor.beautify.repo

sealed class BeautifyResult {
    data class Success(val code: String) : BeautifyResult()
    data class Error(val exception: Exception) : BeautifyResult()
}
