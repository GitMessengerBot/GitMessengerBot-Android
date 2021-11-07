/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubRepoRepository.kt] created by Ji Sungbin on 21. 8. 30. 오후 4:19
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.repo

import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubFile
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubFileContent
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubRepo
import kotlinx.coroutines.CoroutineScope

interface GithubRepoRepository {
    suspend fun getFileContent(
        repoName: String,
        path: String,
        branch: String,
        coroutineScope: CoroutineScope
    ): Result<GithubFileContent>

    suspend fun createRepo(githubRepo: GithubRepo, coroutineScope: CoroutineScope): Result<Unit>

    suspend fun updateFile(
        repoName: String,
        path: String,
        githubFile: GithubFile,
        coroutineScope: CoroutineScope
    ): Result<Unit>
}
