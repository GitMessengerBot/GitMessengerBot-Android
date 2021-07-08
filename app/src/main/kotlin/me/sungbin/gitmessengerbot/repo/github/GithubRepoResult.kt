/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubRepoResult.kt] created by Ji Sungbin on 21. 7. 8. 오후 9:11.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.repo.github

import me.sungbin.gitmessengerbot.repo.github.model.GithubUser

sealed class GithubRepoResult {
    data class Error(val exception: Exception) : GithubRepoResult()
    data class Success(val user: GithubUser) : GithubRepoResult()
}
