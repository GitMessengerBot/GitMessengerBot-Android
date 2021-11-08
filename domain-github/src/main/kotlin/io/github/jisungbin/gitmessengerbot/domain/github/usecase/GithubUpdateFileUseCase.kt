/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubUpdateFileUseCase.kt] created by Ji Sungbin on 21. 8. 30. 오후 4:52
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.usecase

import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubFile
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubRepoRepository
import kotlinx.coroutines.CoroutineScope

class GithubUpdateFileUseCase(private val githubRepoRepository: GithubRepoRepository) {
    suspend operator fun invoke(
        repoName: String,
        path: String,
        githubFile: GithubFile,
        coroutineScope: CoroutineScope
    ) = githubRepoRepository.updateFile(
        repoName = repoName,
        path = path,
        githubFile = githubFile,
        coroutineScope = coroutineScope
    )
}
