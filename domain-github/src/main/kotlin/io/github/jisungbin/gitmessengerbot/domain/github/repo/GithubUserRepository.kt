/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubRepository.kt] created by Ji Sungbin on 21. 8. 28. 오후 11:17
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.repo

import io.github.jisungbin.gitmessengerbot.domain.github.model.user.GithubAouth
import io.github.jisungbin.gitmessengerbot.domain.github.model.user.GithubUser
import kotlinx.coroutines.CoroutineScope

interface GithubUserRepository {
    suspend fun getUserInfo(aouthToken: String, coroutineScope: CoroutineScope): Result<GithubUser>
    suspend fun requestAouthToken(
        requestCode: String,
        coroutineScope: CoroutineScope
    ): Result<GithubAouth>
}
