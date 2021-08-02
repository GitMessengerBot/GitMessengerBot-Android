/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [File.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:21.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.editor.git.model

import io.github.jisungbin.gitmessengerbot.util.config.StringConfig

data class GitFile(
    val message: String,
    val content: String,
    val sha: String,
    val branch: String = StringConfig.GitDefaultBranch
)