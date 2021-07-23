/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Tree.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:46.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.editor.git.model

import com.google.gson.annotations.SerializedName

data class Tree(
    @SerializedName("sha")
    val sha: String,

    @SerializedName("url")
    val url: String
)
