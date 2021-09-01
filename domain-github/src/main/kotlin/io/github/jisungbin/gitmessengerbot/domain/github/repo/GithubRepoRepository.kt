/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubRepoRepository.kt] created by Ji Sungbin on 21. 8. 30. 오후 4:19
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.repo

import io.github.jisungbin.gitmessengerbot.domain.github.GithubResult
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubFile
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubFileContent
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubRepo
import kotlinx.coroutines.flow.Flow

interface GithubRepoRepository {
    fun getFileContent(
        repoName: String,
        path: String,
        branch: String,
    ): Flow<GithubResult<GithubFileContent>>

    fun createRepo(githubRepo: GithubRepo): Flow<GithubResult<Unit>>
    fun updateFile(repoName: String, path: String, githubFile: GithubFile): Flow<GithubResult<Unit>>
}
