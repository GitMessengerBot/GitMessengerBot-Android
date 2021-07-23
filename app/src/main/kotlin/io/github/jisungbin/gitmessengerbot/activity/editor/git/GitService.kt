/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GitService.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:22.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.editor.git

import io.github.jisungbin.gitmessengerbot.activity.editor.git.model.FileContentResponse
import io.github.jisungbin.gitmessengerbot.activity.editor.git.model.FileCreateResponse
import io.github.jisungbin.gitmessengerbot.activity.editor.git.model.GitFile
import io.github.jisungbin.gitmessengerbot.activity.editor.git.model.Repo
import io.github.jisungbin.gitmessengerbot.activity.editor.git.model.RepoCreateResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface GitService {
    @PUT("/repos/{owner}/{repoName}/contents/{path}")
    fun updateFile(
        @Path("owner") owner: String,
        @Path("repoName") repoName: String,
        @Path("path") path: String,
        @Body body: GitFile
    ): Call<FileCreateResponse>

    @POST("/user/repos")
    fun createRepo(
        @Body body: Repo
    ): Call<RepoCreateResponse>

    @GET("/repos/{owner}/{repoName}/contents/{path}")
    fun getFileContent(
        @Path("owner") owner: String,
        @Path("repoName") repoName: String,
        @Path("path") path: String,
        @Query("ref") branch: String
    ): Call<FileContentResponse>
}
