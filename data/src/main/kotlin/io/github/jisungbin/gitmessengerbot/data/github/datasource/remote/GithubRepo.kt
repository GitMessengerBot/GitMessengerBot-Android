/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubRepo.kt] created by Ji Sungbin on 21. 7. 8. 오후 9:08.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.datasource.remote

import io.github.jisungbin.gitmessengerbot.domain.repo.RepoResult
import kotlinx.coroutines.flow.Flow

interface GithubRepo {
    fun getAccessToken(requestCode: String): Flow<RepoResult>
    fun getUserInfo(githubKey: String): Flow<RepoResult>
}
