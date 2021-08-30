/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Plan.kt] created by Ji Sungbin on 21. 8. 30. 오후 3:57
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model

import com.google.gson.annotations.SerializedName

data class Plan(
    @SerializedName("private_repos")
    val privateRepos: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("collaborators")
    val collaborators: Int,

    @SerializedName("space")
    val space: Int,
)
