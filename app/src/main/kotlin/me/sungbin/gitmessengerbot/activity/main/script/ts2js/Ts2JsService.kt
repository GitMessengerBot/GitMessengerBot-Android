/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Ts2JsService.kt] created by Ji Sungbin on 21. 7. 10. 오전 7:28.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.script.ts2js

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Ts2JsService {
    @POST("/convert/typescript/javascript")
    fun convert(@Body js: String): Call<String>
}
