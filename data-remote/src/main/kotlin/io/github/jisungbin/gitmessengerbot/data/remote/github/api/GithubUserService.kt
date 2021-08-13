/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubUserService.kt] created by Ji Sungbin on 21. 8. 2. 오후 8:56.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.remote.github.api

import io.github.jisungbin.gitmessengerbot.data.remote.github.model.GithubUserResponse
import retrofit2.Response
import retrofit2.http.GET

interface GithubUserService {
    @GET("/user")
    suspend fun getUserInfo(): Response<GithubUserResponse>
}
