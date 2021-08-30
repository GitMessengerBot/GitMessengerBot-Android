/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GitRepo.kt] created by Ji Sungbin on 21. 8. 30. 오후 4:29
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.api

import io.github.jisungbin.gitmessengerbot.data.github.model.FileContentResponse
import io.github.jisungbin.gitmessengerbot.data.github.model.FileCreateResponse
import io.github.jisungbin.gitmessengerbot.data.github.model.RepoCreateResponse
import io.github.jisungbin.gitmessengerbot.domain.github.model.GithubRepo
import io.github.jisungbin.gitmessengerbot.domain.github.model.GithubFile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubRepoService {
    @PUT("/repos/{owner}/{repoName}/contents/{path}")
    suspend fun updateFile(
        @Path("owner") owner: String,
        @Path("repoName") repoName: String,
        @Path("path") path: String,
        @Body githubFile: GithubFile,
    ): Response<FileCreateResponse>

    @POST("/user/repos")
    suspend fun createRepo(@Body githubRepo: GithubRepo): Response<RepoCreateResponse>

    @GET("/repos/{owner}/{repoName}/contents/{path}")
    suspend fun getFileContent(
        @Path("owner") owner: String,
        @Path("repoName") repoName: String,
        @Path("path") path: String,
        @Query("ref") branch: String,
    ): Response<FileContentResponse>
}
