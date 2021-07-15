package me.sungbin.gitmessengerbot.activity.setup.github.model

import com.google.gson.annotations.SerializedName

data class GithubTokenResponse(
    @field:SerializedName("access_token")
    val accessToken: String,

    @field:SerializedName("scope")
    val scope: String,

    @field:SerializedName("token_type")
    val tokenType: String
) : GithubResultWrapper
