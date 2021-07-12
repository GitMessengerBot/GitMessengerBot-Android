/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [CompileResult.kt] created by Ji Sungbin on 21. 7. 13. 오전 3:25.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ScriptCompileResult.kt] created by Ji Sungbin on 21. 7. 12. 오후 9:55.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.script.compiler

sealed class CompileResult {
    object Success : CompileResult()
    data class Error(val exception: Exception) : CompileResult()
}
