/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.model

import com.google.gson.annotations.SerializedName

data class Plan(
    @field:SerializedName("private_repos")
    val privateRepos: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("collaborators")
    val collaborators: Int,

    @field:SerializedName("space")
    val space: Int
)
