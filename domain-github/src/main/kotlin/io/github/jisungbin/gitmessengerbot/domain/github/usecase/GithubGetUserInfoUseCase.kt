/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubUserServiceUseCase.kt] created by Ji Sungbin on 21. 8. 10. 오후 7:14.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.usecase

import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubUserRepository
import kotlinx.coroutines.CoroutineScope

class GithubGetUserInfoUseCase(private val githubUserRepository: GithubUserRepository) {
    suspend operator fun invoke(aouthToken: String, coroutineScope: CoroutineScope) =
        githubUserRepository.getUserInfo(aouthToken = aouthToken, coroutineScope = coroutineScope)
}
