/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GitRepo.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:52.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.editor.git.repo

import io.github.jisungbin.gitmessengerbot.activity.editor.git.model.GitFile
import io.github.jisungbin.gitmessengerbot.activity.editor.git.model.Repo
import io.github.jisungbin.gitmessengerbot.repo.Result
import io.github.jisungbin.gitmessengerbot.common.StringConfig
import kotlinx.coroutines.flow.Flow

interface GitRepo {
    fun getFileContent(
        repoName: String,
        path: String,
        branch: String = io.github.jisungbin.gitmessengerbot.common.StringConfig.GitDefaultBranch
    ): Flow<io.github.jisungbin.gitmessengerbot.repo.Result>

    fun createRepo(repo: Repo): Flow<io.github.jisungbin.gitmessengerbot.repo.Result>
    fun updateFile(repoName: String, path: String, gitFile: GitFile): Flow<io.github.jisungbin.gitmessengerbot.repo.Result>
}
