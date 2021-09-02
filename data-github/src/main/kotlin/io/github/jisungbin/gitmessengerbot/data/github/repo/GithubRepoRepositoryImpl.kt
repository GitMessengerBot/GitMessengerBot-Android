/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GitRepoImpl.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:53.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.repo

import io.github.jisungbin.gitmessengerbot.common.config.GithubConfig
import io.github.jisungbin.gitmessengerbot.common.core.Storage
import io.github.jisungbin.gitmessengerbot.common.exception.DataGithubException
import io.github.jisungbin.gitmessengerbot.common.extension.toModel
import io.github.jisungbin.gitmessengerbot.data.github.api.GithubRepoService
import io.github.jisungbin.gitmessengerbot.data.github.mapper.toDomain
import io.github.jisungbin.gitmessengerbot.data.github.util.isValid
import io.github.jisungbin.gitmessengerbot.data.github.util.toFailResult
import io.github.jisungbin.gitmessengerbot.domain.github.GithubResult
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubFile
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubRepo
import io.github.jisungbin.gitmessengerbot.domain.github.model.user.GithubData
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubRepoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Retrofit

class GithubRepoRepositoryImpl(private val retrofit: Retrofit) : GithubRepoRepository {
    private val buildRetrofit by lazy { retrofit.create(GithubRepoService::class.java) }
    private val githubData: GithubData = Storage.read(GithubConfig.DataPath, null)?.toModel()
        ?: throw DataGithubException("GithubConfig.DataPath data is null.")

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFileContent(repoName: String, path: String, branch: String) = callbackFlow {
        try {
            val request = buildRetrofit.getFileContent(githubData.userName, repoName, path, branch)
            trySend(
                if (request.isValid()) {
                    GithubResult.Success(request.body()!!.toDomain())
                } else {
                    request.toFailResult("getFileContent")
                }
            )
        } catch (exception: Exception) {
            trySend(GithubResult.Fail(exception))
        }

        awaitClose { close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun createRepo(githubRepo: GithubRepo) = callbackFlow {
        try {
            val request = buildRetrofit.createRepo(githubRepo)
            trySend(
                if (request.isValid()) {
                    GithubResult.Success(Unit)
                } else {
                    request.toFailResult("createRepo")
                }
            )
        } catch (exception: Exception) {
            trySend(GithubResult.Fail(exception))
        }

        awaitClose { close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun updateFile(
        repoName: String,
        path: String,
        githubFile: GithubFile,
    ) = callbackFlow {
        try {
            val request = buildRetrofit.updateFile(
                owner = githubData.userName,
                repoName = repoName,
                path = path,
                githubFile = githubFile
            )
            trySend(
                if (request.isValid()) {
                    GithubResult.Success(Unit)
                } else {
                    request.toFailResult("updateFile")
                }
            )
        } catch (exception: Exception) {
            trySend(GithubResult.Fail(exception))
        }

        awaitClose { close() }
    }
}
