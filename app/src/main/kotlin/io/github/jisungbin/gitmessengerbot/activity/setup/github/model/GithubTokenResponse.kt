/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubTokenResponse.kt] created by Ji Sungbin on 21. 7. 16. 오후 4:01.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.setup.github.model

import com.google.gson.annotations.SerializedName

data class GithubTokenResponse(
    @field:SerializedName("access_token")
    val accessToken: String,

    @field:SerializedName("scope")
    val scope: String,

    @field:SerializedName("token_type")
    val tokenType: String
) : GithubResultWrapper
