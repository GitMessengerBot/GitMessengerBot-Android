/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubService.kt] created by Ji Sungbin on 21. 5. 21. 오후 4:38.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.setup.github

import me.sungbin.gitmessengerbot.activity.setup.github.model.GithubUserResponse
import retrofit2.Call
import retrofit2.http.GET

interface GithubUserService {
    @GET("/user")
    fun getUserInfo(): Call<GithubUserResponse>
}
