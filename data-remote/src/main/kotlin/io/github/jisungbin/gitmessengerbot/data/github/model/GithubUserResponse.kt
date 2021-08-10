/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubUser.kt] created by Ji Sungbin on 21. 6. 16. 오전 3:22.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model

import com.google.gson.annotations.SerializedName
import io.github.jisungbin.gitmessengerbot.domain.model.github.GithubResultWrapper
import io.github.jisungbin.gitmessengerbot.domain.model.github.Plan

data class GithubUserResponse(
    @SerializedName("gists_url")
    val gistsUrl: String,

    @SerializedName("repos_url")
    val reposUrl: String,

    @SerializedName("two_factor_authentication")
    val twoFactorAuthentication: Boolean,

    @SerializedName("following_url")
    val followingUrl: String,

    @SerializedName("twitter_username")
    val twitterUsername: String,

    @SerializedName("bio")
    val bio: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("login")
    val login: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("blog")
    val blog: String,

    @SerializedName("private_gists")
    val privateGists: Int,

    @SerializedName("total_private_repos")
    val totalPrivateRepos: Int,

    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("site_admin")
    val siteAdmin: Boolean,

    @SerializedName("disk_usage")
    val diskUsage: Int,

    @SerializedName("collaborators")
    val collaborators: Int,

    @SerializedName("company")
    val company: String,

    @SerializedName("owned_private_repos")
    val ownedPrivateRepos: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("public_repos")
    val publicRepos: Int,

    @SerializedName("gravatar_id")
    val gravatarId: String,

    @SerializedName("plan")
    val plan: Plan,

    @SerializedName("email")
    val email: String,

    @SerializedName("organizations_url")
    val organizationsUrl: String,

    @SerializedName("hireable")
    val hireable: Boolean,

    @SerializedName("starred_url")
    val starredUrl: String,

    @SerializedName("followers_url")
    val followersUrl: String,

    @SerializedName("public_gists")
    val publicGists: Int,

    @SerializedName("url")
    val url: String,

    @SerializedName("received_events_url")
    val receivedEventsUrl: String,

    @SerializedName("followers")
    val followers: Int,

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("events_url")
    val eventsUrl: String,

    @SerializedName("html_url")
    val htmlUrl: String,

    @SerializedName("following")
    val following: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("node_id")
    val nodeId: String
) : GithubResultWrapper
