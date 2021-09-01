/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [CommitListResponseItem.kt] created by Ji Sungbin on 21. 9. 1. 오후 9:12
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model.commit

import com.fasterxml.jackson.annotation.JsonProperty
import io.github.jisungbin.gitmessengerbot.data.github.model.repo.Commit

data class CommitListItem(
    @field:JsonProperty("committer")
    val committer: Committer? = null,

    @field:JsonProperty("author")
    val author: Author? = null,

    @field:JsonProperty("html_url")
    val htmlUrl: String? = null,

    @field:JsonProperty("commit")
    val commit: Commit? = null,

    @field:JsonProperty("comments_url")
    val commentsUrl: String? = null,

    @field:JsonProperty("sha")
    val sha: String? = null,

    @field:JsonProperty("url")
    val url: String? = null,

    @field:JsonProperty("node_id")
    val nodeId: String? = null,

    @field:JsonProperty("parents")
    val parents: List<ParentsItem?>? = null,
)
