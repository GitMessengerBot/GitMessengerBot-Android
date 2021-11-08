/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GetCommitContentUseCase.kt] created by Ji Sungbin on 21. 9. 3. 오전 12:20
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.usecase

import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubCommitRepository
import kotlinx.coroutines.CoroutineScope

class GetCommitContentUseCase(private val commitRepository: GithubCommitRepository) {
    suspend operator fun invoke(
        owner: String,
        repoName: String,
        sha: String,
        coroutineScope: CoroutineScope
    ) = commitRepository.getFileCommitContent(
        owner = owner,
        repoName = repoName,
        sha = sha,
        coroutineScope = coroutineScope
    )
}
