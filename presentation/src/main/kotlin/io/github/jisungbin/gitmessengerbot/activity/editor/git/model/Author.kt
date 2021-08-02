/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Author.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:45.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.editor.git.model

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("date")
    val date: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String
)
