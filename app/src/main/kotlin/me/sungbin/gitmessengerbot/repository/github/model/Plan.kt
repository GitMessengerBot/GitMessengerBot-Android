/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Plan.kt] created by Ji Sungbin on 21. 5. 21. 오후 4:37.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.repository.github.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Plan(
    @SerialName("private_repos")
    val privateRepos: Int,

    @SerialName("name")
    val name: String,

    @SerialName("collaborators")
    val collaborators: Int,

    @SerialName("space")
    val space: Int
)
