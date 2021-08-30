/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ResponseToDomainMapper.kt] created by Ji Sungbin on 21. 8. 10. 오후 6:59.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.mapper

import io.github.jisungbin.gitmessengerbot.data.github.model.AouthResponse
import io.github.jisungbin.gitmessengerbot.data.github.model.UserResponse
import io.github.jisungbin.gitmessengerbot.domain.github.model.GithubAouth
import io.github.jisungbin.gitmessengerbot.domain.github.model.GithubUser

fun AouthResponse.toDomain() = GithubAouth(accessToken = accessToken)

fun UserResponse.toDomain() = GithubUser(userName = login, profileImageUrl = avatarUrl)
