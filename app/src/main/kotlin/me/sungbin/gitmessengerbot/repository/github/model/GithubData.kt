/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubData.kt] created by Ji Sungbin on 21. 6. 16. 오전 3:23.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.repository.github.model

import com.google.gson.annotations.SerializedName

data class GithubData(
    @SerializedName("personal_key") val personalKey: String = "",
    @SerializedName("user_name") val userName: String = "",
    @SerializedName("profile_image_url") val profileImageUrl: String = ""
)
