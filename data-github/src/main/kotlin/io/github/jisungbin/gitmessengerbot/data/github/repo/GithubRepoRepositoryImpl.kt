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
import io.github.jisungbin.gitmessengerbot.domain.github.GithubResult
import io.github.jisungbin.gitmessengerbot.domain.github.model.GithubRepo
import io.github.jisungbin.gitmessengerbot.domain.github.model.GithubUser
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubFile
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubRepoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Retrofit

class GithubRepoRepositoryImpl constructor(private val retrofit: Retrofit) : GithubRepoRepository {
    private val buildRetrofit by lazy { retrofit.create(GithubRepoService::class.java) }
    private val githubUser: GithubUser = Storage.read(GithubConfig.DataPath, null)?.toModel()
        ?: throw DataGithubException("GithubConfig.DataPath data is null.")

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFileContent(repoName: String, path: String, branch: String) = callbackFlow {
        try {
            val request = buildRetrofit.getFileContent(githubUser.userName, repoName, path, branch)
            if (request.isValid()) {
                trySend(GithubResult.Success(request.body()!!.toDomain()))
            } else {
                trySend(GithubResult.Fail(DataGithubException("Github.getFileContent response is null.")))
            }
        } catch (exception: Exception) {
            trySend(GithubResult.Fail(exception))
        }

        awaitClose { close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun createRepo(githubRepo: GithubRepo) = callbackFlow {
        try {
            val request = buildRetrofit.createRepo(githubRepo)
            if (request.isValid()) {
                trySend(GithubResult.Success(Unit))
            } else {
                trySend(GithubResult.Fail(DataGithubException("Github.createRepo response is null.")))
            }
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
                owner = githubUser.userName,
                repoName = repoName,
                path = path,
                githubFile = githubFile
            )
            if (request.isValid()) {
                trySend(GithubResult.Success(Unit))
            } else {
                trySend(GithubResult.Fail(DataGithubException("Github.updateFile response is null.")))
            }
        } catch (exception: Exception) {
            trySend(GithubResult.Fail(exception))
        }

        awaitClose { close() }
    }
}
