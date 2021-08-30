/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Owner.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:36.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("gists_url")
    val gistsUrl: String,

    @SerializedName("repos_url")
    val reposUrl: String,

    @SerializedName("following_url")
    val followingUrl: String,

    @SerializedName("starred_url")
    val starredUrl: String,

    @SerializedName("login")
    val login: String,

    @SerializedName("followers_url")
    val followersUrl: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String,

    @SerializedName("received_events_url")
    val receivedEventsUrl: String,

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("events_url")
    val eventsUrl: String,

    @SerializedName("html_url")
    val htmlUrl: String,

    @SerializedName("site_admin")
    val siteAdmin: Boolean,

    @SerializedName("id")
    val id: Int,

    @SerializedName("gravatar_id")
    val gravatarId: String,

    @SerializedName("node_id")
    val nodeId: String,

    @SerializedName("organizations_url")
    val organizationsUrl: String
)
