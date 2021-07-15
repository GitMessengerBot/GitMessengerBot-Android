/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubRepo.kt] created by Ji Sungbin on 21. 7. 8. 오후 9:08.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.setup.github.repo

import kotlinx.coroutines.flow.Flow

interface GithubRepo {
    fun getAccessToken(requestCode: String): Flow<GithubResult>
    fun getUserInfo(githubKey: String): Flow<GithubResult>
}
