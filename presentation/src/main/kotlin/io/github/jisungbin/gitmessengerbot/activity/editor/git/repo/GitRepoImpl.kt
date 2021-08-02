/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GitRepoImpl.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:53.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.editor.git.repo

import io.github.jisungbin.gitmessengerbot.activity.editor.git.GitService
import io.github.jisungbin.gitmessengerbot.activity.editor.git.model.GitFile
import io.github.jisungbin.gitmessengerbot.activity.editor.git.model.Repo
import io.github.jisungbin.gitmessengerbot.activity.setup.github.model.GithubData
import io.github.jisungbin.gitmessengerbot.repo.Result
import io.github.jisungbin.gitmessengerbot.util.Json
import io.github.jisungbin.gitmessengerbot.util.Storage
import io.github.jisungbin.gitmessengerbot.util.config.StringConfig
import io.github.jisungbin.gitmessengerbot.util.extension.toBase64
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Retrofit
import retrofit2.await

class GitRepoImpl @Inject constructor(
    private val retrofit: Retrofit
) : GitRepo {
    private val buildRetrofit by lazy { retrofit.create(GitService::class.java) }
    private val gitUser by lazy {
        Json.toModel(Storage.read(StringConfig.GithubData, null)!!, GithubData::class)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFileContent(repoName: String, path: String, branch: String) = callbackFlow {
        try {
            trySend(
                Result.Success(
                    buildRetrofit.getFileContent(
                        gitUser.userName,
                        repoName,
                        path,
                        branch
                    ).await()
                )
            )
        } catch (exception: Exception) {
            trySend(Result.Fail(exception))
        }

        awaitClose { close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun createRepo(repo: Repo) = callbackFlow {
        try {
            trySend(Result.Success(buildRetrofit.createRepo(repo).await()))
        } catch (exception: Exception) {
            trySend(Result.Fail(exception))
        }

        awaitClose { close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun updateFile(
        repoName: String,
        path: String,
        gitFile: GitFile
    ) = callbackFlow {
        try {
            val response = buildRetrofit.updateFile(
                owner = gitUser.userName,
                repoName = repoName,
                path = path,
                body = gitFile.copy(content = gitFile.content.toBase64())
            ).await()
            trySend(Result.Success(response))
        } catch (exception: Exception) {
            trySend(Result.Fail(exception))
        }

        awaitClose { close() }
    }
}