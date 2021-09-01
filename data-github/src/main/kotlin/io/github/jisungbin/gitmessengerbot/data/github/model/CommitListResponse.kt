/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [CommitListResponse.kt] created by Ji Sungbin on 21. 9. 1. 오후 9:13
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CommitListResponse(
    @field:JsonProperty("CommitListResponse")
    val commitList: List<CommitListItem?>? = null,
)
