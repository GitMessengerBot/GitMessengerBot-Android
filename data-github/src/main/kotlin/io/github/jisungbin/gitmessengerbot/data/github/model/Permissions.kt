/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Permissions.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:36.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.model

import com.google.gson.annotations.SerializedName

data class Permissions(
    @SerializedName("pull")
    val pull: Boolean,

    @SerializedName("admin")
    val admin: Boolean,

    @SerializedName("push")
    val push: Boolean
)
