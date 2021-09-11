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
import io.github.jisungbin.gitmessengerbot.domain.github.GithubResult
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubCommitRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Retrofit

class GithubCommitRepositoryImpl(private val signedRetrofit: Retrofit) : GithubCommitRepository {
    private val api by lazy { signedRetrofit.create(GithubCommitService::class.java) }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getFileCommitHistory(owner: String, repoName: String) = callbackFlow {
        try {
            val request = api.getFileCommitHistory(owner = owner, repoName = repoName)
            trySend(
                if (request.isValid()) {
                    GithubResult.Success(request.body()!!.toDomain())
                } else {
                    request.toFailResult("getFileCommitHistory")
                }
            )
        } catch (exception: Exception) {
            trySend(GithubResult.Fail(exception))
        }

        close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getFileCommitContent(
        owner: String,
        repoName: String,
        sha: String,
    ) = callbackFlow {
        try {
            val request = api.getFileCommitContent(owner = owner, repoName = repoName, sha = sha)
            trySend(
                if (request.isValid()) {
                    GithubResult.Success(request.body()!!.toDomain())
                } else {
                    request.toFailResult("getFileCommitContent")
                }
            )
        } catch (exception: Exception) {
            trySend(GithubResult.Fail(exception))
        }

        close()
    }
}
