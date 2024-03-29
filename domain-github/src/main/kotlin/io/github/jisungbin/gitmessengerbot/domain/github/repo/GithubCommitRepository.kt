/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubCommitRepository.kt] created by Ji Sungbin on 21. 9. 2. 오후 10:17
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.repo

import io.github.jisungbin.gitmessengerbot.domain.github.model.commit.CommitContents
import io.github.jisungbin.gitmessengerbot.domain.github.model.commit.CommitLists
import kotlinx.coroutines.CoroutineScope

interface GithubCommitRepository {
    suspend fun getFileCommitHistory(
        owner: String,
        repoName: String,
        coroutineScope: CoroutineScope
    ): Result<CommitLists>

    suspend fun getFileCommitContent(
        owner: String,
        repoName: String,
        sha: String,
        coroutineScope: CoroutineScope
    ): Result<CommitContents>
}
