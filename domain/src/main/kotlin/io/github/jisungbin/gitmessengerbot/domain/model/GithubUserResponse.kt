/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubUser.kt] created by Ji Sungbin on 21. 6. 16. 오전 3:22.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.model

data class GithubUserResponse(
    val gistsUrl: String,
    val reposUrl: String,
    val twoFactorAuthentication: Boolean,
    val followingUrl: String,
    val twitterUsername: String,
    val bio: String,
    val createdAt: String,
    val login: String,
    val type: String,
    val blog: String,
    val privateGists: Int,
    val totalPrivateRepos: Int,
    val subscriptionsUrl: String,
    val updatedAt: String,
    val siteAdmin: Boolean,
    val diskUsage: Int,
    val collaborators: Int,
    val company: String,
    val ownedPrivateRepos: Int,
    val id: Int,
    val publicRepos: Int,
    val gravatarId: String,
    val plan: Plan,
    val email: String,
    val organizationsUrl: String,
    val hireable: Boolean,
    val starredUrl: String,
    val followersUrl: String,
    val publicGists: Int,
    val url: String,
    val receivedEventsUrl: String,
    val followers: Int,
    val avatarUrl: String,
    val eventsUrl: String,
    val htmlUrl: String,
    val following: Int,
    val name: String,
    val location: String,
    val nodeId: String
) : GithubResultWrapper
