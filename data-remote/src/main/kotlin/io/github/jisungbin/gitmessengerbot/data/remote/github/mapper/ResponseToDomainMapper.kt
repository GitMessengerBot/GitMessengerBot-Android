/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ResponseToDomainMapper.kt] created by Ji Sungbin on 21. 8. 10. 오후 6:59.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.remote.github.mapper

import io.github.jisungbin.gitmessengerbot.data.remote.github.model.GithubAouthResponse
import io.github.jisungbin.gitmessengerbot.data.remote.github.model.GithubUserResponse
import io.github.jisungbin.gitmessengerbot.domain.github.model.github.GithubAouth
import io.github.jisungbin.gitmessengerbot.domain.github.model.github.GithubUser

fun GithubAouthResponse.toDomain() = GithubAouth(accessToken = accessToken)

fun GithubUserResponse.toDomain() = GithubUser(userName = login, profileImageUrl = avatarUrl)
