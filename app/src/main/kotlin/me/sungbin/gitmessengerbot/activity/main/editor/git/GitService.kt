/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GitService.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:22.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.editor.git

import com.google.gson.JsonObject
import me.sungbin.gitmessengerbot.activity.main.editor.git.model.File
import me.sungbin.gitmessengerbot.activity.main.editor.git.model.Repo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GitService {

    @PUT("/repos/{owner}/{repo}/contents/{path}")
    fun updateFile(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String,
        @Body body: File
    ): Call<JsonObject>

    @POST("/user/repos")
    fun createRepo(
        @Body body: Repo
    ): Call<JsonObject>
}