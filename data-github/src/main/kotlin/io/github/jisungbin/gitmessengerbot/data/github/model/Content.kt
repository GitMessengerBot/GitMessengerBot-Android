/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Content.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:45.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model

import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("path")
    val path: String,

    @SerializedName("size")
    val size: Int,

    @SerializedName("_links")
    val links: Links,

    @SerializedName("html_url")
    val htmlUrl: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("download_url")
    val downloadUrl: String,

    @SerializedName("git_url")
    val gitUrl: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("sha")
    val sha: String,

    @SerializedName("url")
    val url: String
)
