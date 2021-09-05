/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [CommitContentItem.kt] created by Ji Sungbin on 21. 9. 2. 오후 10:21
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model.commit

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty

@JsonFormat(with = [JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY])
data class CommitContentItem(
    @field:JsonProperty("patch")
    val patch: String? = null,

    @field:JsonProperty("filename")
    val filename: String? = null,

    @field:JsonProperty("additions")
    val additions: Int? = null,

    @field:JsonProperty("deletions")
    val deletions: Int? = null,

    @field:JsonProperty("changes")
    val changes: Int? = null,

    @field:JsonProperty("raw_url")
    val rawUrl: String? = null,

    @field:JsonProperty("status")
    val status: String? = null,
)
