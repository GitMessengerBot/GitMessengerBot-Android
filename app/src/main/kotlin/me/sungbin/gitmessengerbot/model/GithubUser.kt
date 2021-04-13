/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.model

import com.google.gson.annotations.SerializedName

data class GithubUser(
    @field:SerializedName("gists_url")
    val gistsUrl: String,

    @field:SerializedName("repos_url")
    val reposUrl: String,

    @field:SerializedName("two_factor_authentication")
    val twoFactorAuthentication: Boolean,

    @field:SerializedName("following_url")
    val followingUrl: String,

    @field:SerializedName("twitter_username")
    val twitterUsername: String,

    @field:SerializedName("bio")
    val bio: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("blog")
    val blog: String,

    @field:SerializedName("private_gists")
    val privateGists: Int,

    @field:SerializedName("total_private_repos")
    val totalPrivateRepos: Int,

    @field:SerializedName("subscriptions_url")
    val subscriptionsUrl: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("site_admin")
    val siteAdmin: Boolean,

    @field:SerializedName("disk_usage")
    val diskUsage: Int,

    @field:SerializedName("collaborators")
    val collaborators: Int,

    @field:SerializedName("company")
    val company: String,

    @field:SerializedName("owned_private_repos")
    val ownedPrivateRepos: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("public_repos")
    val publicRepos: Int,

    @field:SerializedName("gravatar_id")
    val gravatarId: String,

    @field:SerializedName("plan")
    val plan: Plan,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("organizations_url")
    val organizationsUrl: String,

    @field:SerializedName("hireable")
    val hireable: Boolean,

    @field:SerializedName("starred_url")
    val starredUrl: String,

    @field:SerializedName("followers_url")
    val followersUrl: String,

    @field:SerializedName("public_gists")
    val publicGists: Int,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("received_events_url")
    val receivedEventsUrl: String,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("events_url")
    val eventsUrl: String,

    @field:SerializedName("html_url")
    val htmlUrl: String,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("node_id")
    val nodeId: String
)
