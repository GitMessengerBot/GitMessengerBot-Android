/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubUserServiceUseCase.kt] created by Ji Sungbin on 21. 8. 10. 오후 7:14.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.usecase.github

import io.github.jisungbin.gitmessengerbot.domain.model.github.GithubUser
import io.github.jisungbin.gitmessengerbot.domain.repository.Result
import io.github.jisungbin.gitmessengerbot.domain.repository.github.GithubRepository
import io.github.jisungbin.gitmessengerbot.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow

private typealias BaseGithubGetUserInfoUseCase = BaseUseCase<String, Flow<Result<GithubUser>>>

class GithubGetUserInfoUseCase(
    private val githubRepository: GithubRepository
) : BaseGithubGetUserInfoUseCase {
    override suspend fun invoke(parameter: String) = githubRepository.getUserInfo(githubKey = parameter)
}
