/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GitFile.kt] created by Ji Sungbin on 21. 8. 30. 오후 4:27
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.model.repo

data class GithubFile(
    val message: String,
    val content: String,
    val sha: String,
    val branch: String,
)
