/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubUpdateFileUseCase.kt] created by Ji Sungbin on 21. 8. 30. 오후 4:52
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.usecase

import io.github.jisungbin.gitmessengerbot.domain.github.BaseUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.GithubResult
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubRepoRepository
import kotlinx.coroutines.flow.Flow

private typealias BaseGithubUpdateFileUseCase = BaseUseCase<String, Unit>

class GithubUpdateFileUseCase(
    private val githubRepoRepository: GithubRepoRepository,
) : BaseGithubUpdateFileUseCase {
    override suspend fun invoke(parameter: String): Flow<GithubResult<Unit>> {
        TODO("Not yet implemented")
    }
}
