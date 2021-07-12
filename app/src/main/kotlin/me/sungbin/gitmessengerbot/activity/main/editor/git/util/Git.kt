/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Git.kt] created by Ji Sungbin on 21. 7. 13. 오전 2:20.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.editor.git.util

import me.sungbin.gitmessengerbot.util.Storage
import me.sungbin.gitmessengerbot.util.config.PathConfig

object Git {
    fun getSha(repoName: String) = Storage.read(PathConfig.GitCommitSha(repoName), "")!!
}
