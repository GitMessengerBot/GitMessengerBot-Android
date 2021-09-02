/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [CommitListResponseItem.kt] created by Ji Sungbin on 21. 9. 2. 오후 10:41
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model.commit

import com.fasterxml.jackson.annotation.JsonProperty

data class CommitListResponseItem(
    @field:JsonProperty("html_url")
    val htmlUrl: String? = null,

    @field:JsonProperty("commit")
    val commit: Commit? = null,

    @field:JsonProperty("sha")
    val sha: String? = null,
)
