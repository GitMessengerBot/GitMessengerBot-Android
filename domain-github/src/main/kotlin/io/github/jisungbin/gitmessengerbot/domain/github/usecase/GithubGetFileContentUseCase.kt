/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubGetFileContentUseCase.kt] created by Ji Sungbin on 21. 8. 30. 오후 4:53
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.usecase

import io.github.jisungbin.gitmessengerbot.domain.github.BaseUseCase3
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubFileContent
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubRepoRepository

private typealias BaseGithubGetFileContentUseCase = BaseUseCase3<String, String, String, GithubFileContent>

class GithubGetFileContentUseCase(
    private val githubRepoRepository: GithubRepoRepository,
) : BaseGithubGetFileContentUseCase {
    override suspend fun invoke(
        parameter: String,
        parameter2: String,
        parameter3: String,
    ) = githubRepoRepository.getFileContent(
        repoName = parameter,
        path = parameter2,
        branch = parameter3
    )
}
