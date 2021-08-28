/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubUserServiceUseCase.kt] created by Ji Sungbin on 21. 8. 10. 오후 7:14.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.usecase

import io.github.jisungbin.gitmessengerbot.domain.github.BaseUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.model.GithubUser
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubRepository

private typealias BaseGithubGetUserInfoUseCase = BaseUseCase<String, GithubUser>

class GithubGetUserInfoUseCase(
    private val githubRepository: GithubRepository,
) : BaseGithubGetUserInfoUseCase {
    override suspend fun invoke(parameter: String) =
        githubRepository.getUserInfo(githubKey = parameter)
}
