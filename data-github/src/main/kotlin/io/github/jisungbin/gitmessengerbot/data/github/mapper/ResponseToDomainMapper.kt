/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ResponseToDomainMapper.kt] created by Ji Sungbin on 21. 8. 10. 오후 6:59.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.mapper

import io.github.jisungbin.gitmessengerbot.common.exception.DataGithubException
import io.github.jisungbin.gitmessengerbot.data.github.model.repo.FileContentResponse
import io.github.jisungbin.gitmessengerbot.data.github.model.user.AouthResponse
import io.github.jisungbin.gitmessengerbot.data.github.model.user.UserResponse
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubFileContent
import io.github.jisungbin.gitmessengerbot.domain.github.model.user.GithubAouth
import io.github.jisungbin.gitmessengerbot.domain.github.model.user.GithubUser

private fun exception(field: String) =
    DataGithubException("Github response required field is null. ($field)")

fun AouthResponse.toDomain() = GithubAouth(token = accessToken ?: throw exception("token"))

fun UserResponse.toDomain() = GithubUser(
    userName = login ?: throw exception("userName"),
    profileImageUrl = avatarUrl ?: throw exception("profileImageUrl")
)

fun FileContentResponse.toDomain() = GithubFileContent(
    downloadUrl = downloadUrl ?: throw exception("downloadUrl"),
    sha = sha ?: throw exception("sha")
)
