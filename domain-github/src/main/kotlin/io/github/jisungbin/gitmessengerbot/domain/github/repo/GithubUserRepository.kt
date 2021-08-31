/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubRepository.kt] created by Ji Sungbin on 21. 8. 28. 오후 11:17
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.repo

import io.github.jisungbin.gitmessengerbot.domain.github.GithubResult
import io.github.jisungbin.gitmessengerbot.domain.github.model.GithubAouth
import io.github.jisungbin.gitmessengerbot.domain.github.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface GithubUserRepository {
    suspend fun requestAouthToken(requestCode: String): Flow<GithubResult<GithubAouth>>
    suspend fun getUserInfo(aouthToken: String): Flow<GithubResult<GithubUser>>
}
