/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.repo.github.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Ji Sungbin on 2021/04/13.
 */

data class GithubData(
    @SerializedName("personal_key") val personalKey: String = "",
    @SerializedName("user_name") val userName: String = "",
    @SerializedName("profile_image_url") val profileImageUrl: String = ""
)
