/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GitResult.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:49.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.editor.git.repo

import me.sungbin.gitmessengerbot.activity.main.editor.git.model.GitResultWrapper

sealed class GitResult {
    data class Success(val result: GitResultWrapper) : GitResult()
    data class Error(val exception: Exception) : GitResult()
}
