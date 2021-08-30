/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubCreateRepoUseCase.kt] created by Ji Sungbin on 21. 8. 30. 오후 4:52
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.usecase

import io.github.jisungbin.gitmessengerbot.domain.github.BaseUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.model.GithubRepo
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubRepoRepository

private typealias BaseGithubCreateRepoUseCase = BaseUseCase<GithubRepo, Unit>

class GithubCreateRepoUseCase(
    private val githubRepoRepository: GithubRepoRepository,
) : BaseGithubCreateRepoUseCase {
    override suspend fun invoke(parameter: GithubRepo) =
        githubRepoRepository.createRepo(githubRepo = parameter)
}
