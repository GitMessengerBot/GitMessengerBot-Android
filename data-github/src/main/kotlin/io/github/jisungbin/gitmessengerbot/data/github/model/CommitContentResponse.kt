/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [CommitContentResponse.kt] created by Ji Sungbin on 21. 9. 1. 오후 7:40
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CommitContentResponse(
    @field:JsonProperty("committer")
    val committer: Committer?,

    @field:JsonProperty("stats")
    val stats: Stats?,

    @field:JsonProperty("author")
    val author: Author?,

    @field:JsonProperty("html_url")
    val htmlUrl: String?,

    @field:JsonProperty("commit")
    val commit: Commit?,

    @field:JsonProperty("comments_url")
    val commentsUrl: String?,

    @field:JsonProperty("files")
    val files: List<FilesItem>?,

    @field:JsonProperty("sha")
    val sha: String?,

    @field:JsonProperty("url")
    val url: String?,

    @field:JsonProperty("node_id")
    val nodeId: String?,

    @field:JsonProperty("parents")
    val parents: List<ParentsItem>
)
