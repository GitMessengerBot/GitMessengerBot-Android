/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [FileCreateResponse.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:46.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.editor.git.model

import com.google.gson.annotations.SerializedName

data class FileCreateResponse(
    @SerializedName("commit")
    val commit: Commit,

    @SerializedName("content")
    val content: Content
) : GitResultWrapper
