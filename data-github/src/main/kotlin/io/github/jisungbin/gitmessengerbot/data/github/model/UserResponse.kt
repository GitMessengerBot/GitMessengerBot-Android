/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubUser.kt] created by Ji Sungbin on 21. 6. 16. 오전 3:22.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserResponse(
    @field:JsonProperty("gists_url")
    val gistsUrl: String?,

    @field:JsonProperty("repos_url")
    val reposUrl: String?,

    @field:JsonProperty("two_factor_authentication")
    val twoFactorAuthentication: Boolean?,

    @field:JsonProperty("following_url")
    val followingUrl: String?,

    @field:JsonProperty("twitter_username")
    val twitterUsername: String?,

    @field:JsonProperty("bio")
    val bio: String?,

    @field:JsonProperty("created_at")
    val createdAt: String?,

    @field:JsonProperty("login")
    val login: String?,

    @field:JsonProperty("type")
    val type: String?,

    @field:JsonProperty("blog")
    val blog: String?,

    @field:JsonProperty("private_gists")
    val privateGists: Int?,

    @field:JsonProperty("total_private_repos")
    val totalPrivateRepos: Int?,

    @field:JsonProperty("subscriptions_url")
    val subscriptionsUrl: String?,

    @field:JsonProperty("updated_at")
    val updatedAt: String?,

    @field:JsonProperty("site_admin")
    val siteAdmin: Boolean?,

    @field:JsonProperty("disk_usage")
    val diskUsage: Int?,

    @field:JsonProperty("collaborators")
    val collaborators: Int?,

    @field:JsonProperty("company")
    val company: String?,

    @field:JsonProperty("owned_private_repos")
    val ownedPrivateRepos: Int?,

    @field:JsonProperty("id")
    val id: Int?,

    @field:JsonProperty("public_repos")
    val publicRepos: Int?,

    @field:JsonProperty("gravatar_id")
    val gravatarId: String?,

    @field:JsonProperty("plan")
    val plan: Plan?,

    @field:JsonProperty("email")
    val email: String?,

    @field:JsonProperty("organizations_url")
    val organizationsUrl: String?,

    @field:JsonProperty("hireable")
    val hireable: Boolean?,

    @field:JsonProperty("starred_url")
    val starredUrl: String?,

    @field:JsonProperty("followers_url")
    val followersUrl: String?,

    @field:JsonProperty("public_gists")
    val publicGists: Int?,

    @field:JsonProperty("url")
    val url: String?,

    @field:JsonProperty("received_events_url")
    val receivedEventsUrl: String?,

    @field:JsonProperty("followers")
    val followers: Int?,

    @field:JsonProperty("avatar_url")
    val avatarUrl: String?,

    @field:JsonProperty("events_url")
    val eventsUrl: String?,

    @field:JsonProperty("html_url")
    val htmlUrl: String?,

    @field:JsonProperty("following")
    val following: Int?,

    @field:JsonProperty("name")
    val name: String?,

    @field:JsonProperty("location")
    val location: String?,

    @field:JsonProperty("node_id")
    val nodeId: String?,
)
