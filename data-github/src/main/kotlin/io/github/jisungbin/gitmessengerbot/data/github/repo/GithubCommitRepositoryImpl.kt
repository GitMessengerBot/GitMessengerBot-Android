/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubCommitRepositoryImpl.kt] created by Ji Sungbin on 21. 9. 2. 오후 11:57
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.repo

import io.github.jisungbin.gitmessengerbot.data.github.api.GithubCommitService
import io.github.jisungbin.gitmessengerbot.data.github.mapper.toDomain
import io.github.jisungbin.gitmessengerbot.data.github.util.isValid
import io.github.jisungbin.gitmessengerbot.data.github.util.toFailResult
import io.github.jisungbin.gitmessengerbot.domain.github.model.commit.CommitContents
import io.github.jisungbin.gitmessengerbot.domain.github.model.commit.CommitLists
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubCommitRepository
import kotlin.coroutines.resume
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Retrofit

class GithubCommitRepositoryImpl(signedRetrofit: Retrofit) : GithubCommitRepository {
    private val api = signedRetrofit.create(GithubCommitService::class.java)

    override suspend fun getFileCommitHistory(
        owner: String,
        repoName: String,
        coroutineScope: CoroutineScope
    ): Result<CommitLists> = suspendCancellableCoroutine { continuation ->
        coroutineScope.launch {
            try {
                val request = api.getFileCommitHistory(owner = owner, repoName = repoName)
                continuation.resume(
                    if (request.isValid()) {
                        Result.success(request.body()!!.toDomain())
                    } else {
                        request.toFailResult("getFileCommitHistory")
                    }
                )
            } catch (exception: Exception) {
                continuation.resume(Result.failure(exception))
            }
        }
    }

    override suspend fun getFileCommitContent(
        owner: String,
        repoName: String,
        sha: String,
        coroutineScope: CoroutineScope
    ): Result<CommitContents> = suspendCancellableCoroutine { continuation ->
        coroutineScope.launch {
            try {
                val request =
                    api.getFileCommitContent(owner = owner, repoName = repoName, sha = sha)
                continuation.resume(
                    if (request.isValid()) {
                        Result.success(request.body()!!.toDomain())
                    } else {
                        request.toFailResult("getFileCommitContent")
                    }
                )
            } catch (exception: Exception) {
                continuation.resume(Result.failure(exception))
            }
        }
    }
}
