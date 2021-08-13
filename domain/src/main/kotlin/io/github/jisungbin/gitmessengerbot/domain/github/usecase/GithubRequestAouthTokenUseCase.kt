/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubRequestAouthTokenUseCase.kt] created by Ji Sungbin on 21. 8. 13. 오후 8:11.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.usecase

import io.github.jisungbin.gitmessengerbot.domain.BaseUseCase
import io.github.jisungbin.gitmessengerbot.domain.Result
import io.github.jisungbin.gitmessengerbot.domain.github.model.GithubAouth
import io.github.jisungbin.gitmessengerbot.domain.github.repository.GithubRepository
import kotlinx.coroutines.flow.Flow

private typealias BaseGithubRequestAouthTokenUseCase = BaseUseCase<String, Flow<Result<GithubAouth>>>

class GithubRequestAouthTokenUseCase(
    private val githubRepository: GithubRepository
) : BaseGithubRequestAouthTokenUseCase {
    override suspend fun invoke(parameter: String) =
        githubRepository.requestAouthToken(requestCode = parameter)
}
