/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GetCommitHistoryUseCase.kt] created by Ji Sungbin on 21. 9. 3. 오전 12:17
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.usecase

import io.github.jisungbin.gitmessengerbot.domain.github.BaseUseCase2
import io.github.jisungbin.gitmessengerbot.domain.github.model.commit.CommitLists
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubCommitRepository

private typealias BaseCommitHistoryUseCase = BaseUseCase2<String, String, CommitLists>

class GetCommitHistoryUseCase(
    private val commitRepository: GithubCommitRepository,
) : BaseCommitHistoryUseCase {
    override suspend fun invoke(
        parameter: String,
        parameter2: String,
    ) = commitRepository.getFileCommitHistory(parameter, parameter2)
}
