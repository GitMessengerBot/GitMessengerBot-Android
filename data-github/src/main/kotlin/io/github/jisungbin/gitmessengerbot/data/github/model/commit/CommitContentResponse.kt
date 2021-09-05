/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Response.kt] created by Ji Sungbin on 21. 9. 5. 오후 8:07
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model.commit

import com.fasterxml.jackson.annotation.JsonProperty

data class CommitContentResponse(
    @field:JsonProperty("html_url")
    val htmlUrl: String? = null,

    @field:JsonProperty("files")
    val files: List<CommitContentItem?>? = null,
)
