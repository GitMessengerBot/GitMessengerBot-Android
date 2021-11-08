/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubData.kt] created by Ji Sungbin on 21. 6. 16. 오전 3:23.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.model.user

data class GithubData(
    val aouthToken: String = "",
    val userName: String = "",
    val profileImageUrl: String = "",
)
