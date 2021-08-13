/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubAouthService.kt] created by Ji Sungbin on 21. 8. 2. 오후 8:57.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.remote.github.api

import io.github.jisungbin.gitmessengerbot.data.remote.github.model.GithubAouthResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface GithubAouthService {
    @POST("/login/oauth/access_token")
    fun requestAouthToken(
        @Query("code") requestCode: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String
    ): Call<GithubAouthResponse>
}
