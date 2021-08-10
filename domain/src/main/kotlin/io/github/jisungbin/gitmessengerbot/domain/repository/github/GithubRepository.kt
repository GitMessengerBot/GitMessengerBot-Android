/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubRepo.kt] created by Ji Sungbin on 21. 7. 8. 오후 9:08.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.repository.github

import io.github.jisungbin.gitmessengerbot.domain.model.github.GithubAouth
import io.github.jisungbin.gitmessengerbot.domain.model.github.GithubUser
import io.github.jisungbin.gitmessengerbot.domain.repository.Result
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun requestAouthToken(requestCode: String): Flow<Result<GithubAouth>>
    suspend fun getUserInfo(githubKey: String): Flow<Result<GithubUser>>
}
