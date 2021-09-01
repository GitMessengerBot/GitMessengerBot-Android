/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Tree.kt] created by Ji Sungbin on 21. 8. 30. 오후 3:56
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Tree(
    @field:JsonProperty("sha")
    val sha: String?,

    @field:JsonProperty("url")
    val url: String?,
)
