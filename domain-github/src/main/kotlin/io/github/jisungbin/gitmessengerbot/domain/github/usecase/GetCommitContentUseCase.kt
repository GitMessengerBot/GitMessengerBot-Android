/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GetCommitContentUseCase.kt] created by Ji Sungbin on 21. 9. 3. 오전 12:20
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.usecase

import io.github.jisungbin.gitmessengerbot.domain.github.BaseUseCase3
import io.github.jisungbin.gitmessengerbot.domain.github.model.commit.CommitContents
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubCommitRepository

private typealias BaseCommitContentUseCase = BaseUseCase3<String, String, String, CommitContents>

class GetCommitContentUseCase(
    private val commitRepository: GithubCommitRepository,
) : BaseCommitContentUseCase {
    override suspend fun invoke(
        parameter: String,
        parameter2: String,
        parameter3: String,
    ) = commitRepository.getFileCommitContent(
        owner = parameter,
        repoName = parameter2,
        sha = parameter3
    )
}
