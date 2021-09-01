/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Stats.kt] created by Ji Sungbin on 21. 9. 1. 오후 7:40
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model.commit

import com.fasterxml.jackson.annotation.JsonProperty

data class Stats(
    @field:JsonProperty("total")
    val total: Int?,

    @field:JsonProperty("additions")
    val additions: Int?,

    @field:JsonProperty("deletions")
    val deletions: Int?,
)
