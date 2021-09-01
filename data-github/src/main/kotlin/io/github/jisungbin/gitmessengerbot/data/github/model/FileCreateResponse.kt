/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [FileCreateResponse.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:46.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model

import com.fasterxml.jackson.annotation.JsonProperty

data class FileCreateResponse(
    @field:JsonProperty("commit")
    val commit: Commit?,

    @field:JsonProperty("content")
    val content: Content?,
)
