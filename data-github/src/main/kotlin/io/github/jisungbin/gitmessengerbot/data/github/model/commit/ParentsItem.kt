/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ParentsItem.kt] created by Ji Sungbin on 21. 9. 1. 오후 7:40
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model.commit

import com.fasterxml.jackson.annotation.JsonProperty

data class ParentsItem(
    @field:JsonProperty("html_url")
    val htmlUrl: String?,

    @field:JsonProperty("sha")
    val sha: String?,

    @field:JsonProperty("url")
    val url: String?,
)
