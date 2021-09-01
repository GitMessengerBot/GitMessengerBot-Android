/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubCommitRepo.kt] created by Ji Sungbin on 21. 9. 1. 오후 9:13
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.api

interface GithubCommitService {
    /*@GET("/repos/{owner}/{repoName}/commits")
    suspend fun getFileCommitHistory(
        @Path("owner") owner: String,
        @Path("repoName") repoName: String,
    ): Response<CommitContentResponse>

    @GET("/repos/{owner}/{repoName}/commits")
    suspend fun getFileCommitContent(
        @Path("owner") owner: String,
        @Path("repoName") repoName: String,
        @Query("ref") sha: String,
    ): Response<CommitListResponse>*/
}
