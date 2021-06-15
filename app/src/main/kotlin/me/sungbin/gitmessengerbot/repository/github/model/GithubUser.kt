/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubUser.kt] created by Ji Sungbin on 21. 5. 21. 오후 4:37.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.repository.github.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubUser(
    @SerialName("gists_url")
    val gistsUrl: String,

    @SerialName("repos_url")
    val reposUrl: String,

    @SerialName("two_factor_authentication")
    val twoFactorAuthentication: Boolean,

    @SerialName("following_url")
    val followingUrl: String,

    @SerialName("twitter_username")
    val twitterUsername: String,

    @SerialName("bio")
    val bio: String,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("login")
    val login: String,

    @SerialName("type")
    val type: String,

    @SerialName("blog")
    val blog: String,

    @SerialName("private_gists")
    val privateGists: Int,

    @SerialName("total_private_repos")
    val totalPrivateRepos: Int,

    @SerialName("subscriptions_url")
    val subscriptionsUrl: String,

    @SerialName("updated_at")
    val updatedAt: String,

    @SerialName("site_admin")
    val siteAdmin: Boolean,

    @SerialName("disk_usage")
    val diskUsage: Int,

    @SerialName("collaborators")
    val collaborators: Int,

    @SerialName("company")
    val company: String,

    @SerialName("owned_private_repos")
    val ownedPrivateRepos: Int,

    @SerialName("id")
    val id: Int,

    @SerialName("public_repos")
    val publicRepos: Int,

    @SerialName("gravatar_id")
    val gravatarId: String,

    @SerialName("plan")
    val plan: Plan,

    @SerialName("email")
    val email: String,

    @SerialName("organizations_url")
    val organizationsUrl: String,

    @SerialName("hireable")
    val hireable: Boolean,

    @SerialName("starred_url")
    val starredUrl: String,

    @SerialName("followers_url")
    val followersUrl: String,

    @SerialName("public_gists")
    val publicGists: Int,

    @SerialName("url")
    val url: String,

    @SerialName("received_events_url")
    val receivedEventsUrl: String,

    @SerialName("followers")
    val followers: Int,

    @SerialName("avatar_url")
    val avatarUrl: String,

    @SerialName("events_url")
    val eventsUrl: String,

    @SerialName("html_url")
    val htmlUrl: String,

    @SerialName("following")
    val following: Int,

    @SerialName("name")
    val name: String,

    @SerialName("location")
    val location: String,

    @SerialName("node_id")
    val nodeId: String
)
