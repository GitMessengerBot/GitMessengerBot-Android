/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Ts2JsResult.kt] created by Ji Sungbin on 21. 7. 10. 오전 7:29.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.script.ts2js

sealed class Ts2JsResult {
    data class Success(val ts2js: Ts2Js) : Ts2JsResult()
    data class Error(val exception: Exception) : Ts2JsResult()
}
