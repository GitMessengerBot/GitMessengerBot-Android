/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubRequestAouthTokenUseCase.kt] created by Ji Sungbin on 21. 8. 13. 오후 8:11.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.usecase

import io.github.jisungbin.gitmessengerbot.domain.github.BaseUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.model.GithubAouth
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubUserRepository

private typealias BaseGithubRequestAouthTokenUseCase = BaseUseCase<String, GithubAouth>

class GithubRequestAouthTokenUseCase(
    private val githubUserRepository: GithubUserRepository,
) : BaseGithubRequestAouthTokenUseCase {
    override suspend fun invoke(parameter: String) =
        githubUserRepository.requestAouthToken(requestCode = parameter)
}
