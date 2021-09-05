/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [CommitContentResponse.kt] created by Ji Sungbin on 21. 9. 2. 오후 10:21
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model.commit

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
data class CommitContentResponse(
    val files: List<CommitContentItem?>? = null,
)
