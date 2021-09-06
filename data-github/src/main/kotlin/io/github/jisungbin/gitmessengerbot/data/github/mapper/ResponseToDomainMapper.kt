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
import io.github.jisungbin.gitmessengerbot.data.github.model.commit.CommitContentItem
import io.github.jisungbin.gitmessengerbot.data.github.model.commit.CommitContentResponse
import io.github.jisungbin.gitmessengerbot.data.github.model.commit.CommitListItem
import io.github.jisungbin.gitmessengerbot.data.github.model.repo.FileContentResponse
import io.github.jisungbin.gitmessengerbot.data.github.model.user.AouthResponse
import io.github.jisungbin.gitmessengerbot.data.github.model.user.UserResponse
import io.github.jisungbin.gitmessengerbot.domain.github.model.commit.CommitContents
import io.github.jisungbin.gitmessengerbot.domain.github.model.commit.CommitLists
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubFileContent
import io.github.jisungbin.gitmessengerbot.domain.github.model.user.GithubAouth
import io.github.jisungbin.gitmessengerbot.domain.github.model.user.GithubUser

private fun exception(field: String) =
    DataGithubException("Github response required field is null. ($field)")

private fun commitListItemToDomain(item: CommitListItem) =
    io.github.jisungbin.gitmessengerbot.domain.github.model.commit.CommitListItem(
        sha = item.sha ?: throw exception("sha"),
        htmlUrl = item.htmlUrl ?: throw exception("htmlUrl"),
        message = item.commit?.message ?: throw exception("message"),
        date = item.commit.committer?.date ?: throw exception("date"),
        name = item.commit.committer.name ?: throw exception("name"),
    )

private fun commitContentItemToDomain(item: CommitContentItem) =
    io.github.jisungbin.gitmessengerbot.domain.github.model.commit.CommitContentItem(
        patch = item.patch ?: "", // nullable
        filename = item.filename ?: throw exception("filename"),
        additions = item.additions ?: throw exception("additions"),
        deletions = item.deletions ?: throw exception("deletions"),
        changes = item.changes ?: throw exception("changes"),
        rawUrl = item.rawUrl ?: throw exception("rawUrl"),
        status = item.status ?: throw exception("status"),
    )

fun AouthResponse.toDomain() = GithubAouth(token = accessToken ?: throw exception("token"))

fun UserResponse.toDomain() = GithubUser(
    userName = login ?: throw exception("userName"),
    profileImageUrl = avatarUrl ?: throw exception("profileImageUrl")
)

fun FileContentResponse.toDomain() = GithubFileContent(
    downloadUrl = downloadUrl ?: throw exception("downloadUrl"),
    sha = sha ?: throw exception("sha")
)

fun List<CommitListItem?>.toDomain() = CommitLists(
    value = filterNotNull().map(::commitListItemToDomain)
)

fun CommitContentResponse.toDomain() = CommitContents(
    value = files?.filterNotNull()?.map(::commitContentItemToDomain) ?: throw exception("files")
)
