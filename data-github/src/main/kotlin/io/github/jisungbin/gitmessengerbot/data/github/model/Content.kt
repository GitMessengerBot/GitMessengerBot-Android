/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Content.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:45.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Content(
    @field:JsonProperty("path")
    val path: String,

    @field:JsonProperty("size")
    val size: Int,

    @field:JsonProperty("_links")
    val links: Links,

    @field:JsonProperty("html_url")
    val htmlUrl: String,

    @field:JsonProperty("name")
    val name: String,

    @field:JsonProperty("download_url")
    val downloadUrl: String,

    @field:JsonProperty("git_url")
    val gitUrl: String,

    @field:JsonProperty("type")
    val type: String,

    @field:JsonProperty("sha")
    val sha: String,

    @field:JsonProperty("url")
    val url: String
)
