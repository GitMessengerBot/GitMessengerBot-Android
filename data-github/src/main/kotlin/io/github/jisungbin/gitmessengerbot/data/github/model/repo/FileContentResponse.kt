/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [FileContentResponse.kt] created by Ji Sungbin on 21. 7. 16. 오후 4:03.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model.repo

import com.fasterxml.jackson.annotation.JsonProperty

data class FileContentResponse(
    @field:JsonProperty("download_url")
    val downloadUrl: String?,

    @field:JsonProperty("sha")
    val sha: String?,
)
