/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Commit.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:45.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model.repo

import com.fasterxml.jackson.annotation.JsonProperty
import io.github.jisungbin.gitmessengerbot.data.github.model.commit.Author
import io.github.jisungbin.gitmessengerbot.data.github.model.commit.Committer
import io.github.jisungbin.gitmessengerbot.data.github.model.commit.Tree

data class Commit(
    @field:JsonProperty("committer")
    val committer: Committer?,

    @field:JsonProperty("author")
    val author: Author?,

    @field:JsonProperty("html_url")
    val htmlUrl: String?,

    @field:JsonProperty("tree")
    val tree: Tree?,

    @field:JsonProperty("message")
    val message: String?,

    @field:JsonProperty("sha")
    val sha: String?,

    @field:JsonProperty("url")
    val url: String?,

    @field:JsonProperty("verification")
    val verification: Verification?,

    @field:JsonProperty("node_id")
    val nodeId: String?,

    @field:JsonProperty("parents")
    val parents: List<Any>?,
)
