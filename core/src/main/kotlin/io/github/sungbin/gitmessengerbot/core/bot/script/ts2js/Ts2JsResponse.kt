/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Ts2Js.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:11.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.bot.script.ts2js

import com.fasterxml.jackson.annotation.JsonProperty

internal data class Ts2JsResponse(
    @field:JsonProperty("diagnostics")
    val diagnostics: List<Any>,

    @field:JsonProperty("outputText")
    val jsCode: String,
)
