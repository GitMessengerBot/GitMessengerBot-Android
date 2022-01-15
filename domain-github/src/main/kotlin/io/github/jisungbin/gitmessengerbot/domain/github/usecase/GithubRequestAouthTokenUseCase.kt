/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubRequestAouthTokenUseCase.kt] created by Ji Sungbin on 21. 8. 13. 오후 8:11.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.usecase

import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubUserRepository
import kotlinx.coroutines.CoroutineScope

class GithubRequestAouthTokenUseCase(private val githubUserRepository: GithubUserRepository) {
    suspend operator fun invoke(requestCode: String, coroutineScope: CoroutineScope) =
        githubUserRepository.requestAouthToken(
            requestCode = requestCode,
            coroutineScope = coroutineScope
        )
}
