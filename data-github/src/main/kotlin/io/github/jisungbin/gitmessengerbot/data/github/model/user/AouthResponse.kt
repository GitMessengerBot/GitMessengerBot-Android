/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubTokenResponse.kt] created by Ji Sungbin on 21. 7. 16. 오후 4:01.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model.user

import com.fasterxml.jackson.annotation.JsonProperty

data class AouthResponse(
    @field:JsonProperty("access_token")
    val accessToken: String? = null,
)
