/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [CompileResult.kt] created by Ji Sungbin on 21. 7. 10. 오전 11:25.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.bot

sealed class CompileResult {
    object Success : CompileResult()
    data class Error(val exception: Exception) : CompileResult()
}
