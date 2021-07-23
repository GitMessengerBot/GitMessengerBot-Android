/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Ts2Js.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:11.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.main.script.ts2js

import com.google.gson.annotations.SerializedName

data class Ts2JsResponse(
    @SerializedName("diagnostics")
    val diagnostics: List<Any>,

    @SerializedName("outputText")
    val tsCode: String
)
