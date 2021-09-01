/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubUpdateFileUseCase.kt] created by Ji Sungbin on 21. 8. 30. 오후 4:52
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.usecase

import io.github.jisungbin.gitmessengerbot.domain.github.BaseUseCase3
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubFile
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubRepoRepository

private typealias BaseGithubUpdateFileUseCase = BaseUseCase3<String, String, GithubFile, Unit>

class GithubUpdateFileUseCase(
    private val githubRepoRepository: GithubRepoRepository,
) : BaseGithubUpdateFileUseCase {
    override suspend fun invoke(
        parameter: String,
        parameter2: String,
        parameter3: GithubFile,
    ) = githubRepoRepository.updateFile(
        repoName = parameter,
        path = parameter2,
        githubFile = parameter3
    )
}
