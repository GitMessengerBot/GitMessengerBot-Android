/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Owner.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:36.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model.repo

import com.fasterxml.jackson.annotation.JsonProperty

data class Owner(
    @field:JsonProperty("gists_url")
    val gistsUrl: String?,

    @field:JsonProperty("repos_url")
    val reposUrl: String?,

    @field:JsonProperty("following_url")
    val followingUrl: String?,

    @field:JsonProperty("starred_url")
    val starredUrl: String?,

    @field:JsonProperty("login")
    val login: String?,

    @field:JsonProperty("followers_url")
    val followersUrl: String?,

    @field:JsonProperty("type")
    val type: String?,

    @field:JsonProperty("url")
    val url: String?,

    @field:JsonProperty("subscriptions_url")
    val subscriptionsUrl: String?,

    @field:JsonProperty("received_events_url")
    val receivedEventsUrl: String?,

    @field:JsonProperty("avatar_url")
    val avatarUrl: String?,

    @field:JsonProperty("events_url")
    val eventsUrl: String?,

    @field:JsonProperty("html_url")
    val htmlUrl: String?,

    @field:JsonProperty("site_admin")
    val siteAdmin: Boolean?,

    @field:JsonProperty("id")
    val id: Int?,

    @field:JsonProperty("gravatar_id")
    val gravatarId: String?,

    @field:JsonProperty("node_id")
    val nodeId: String?,

    @field:JsonProperty("organizations_url")
    val organizationsUrl: String,
)
