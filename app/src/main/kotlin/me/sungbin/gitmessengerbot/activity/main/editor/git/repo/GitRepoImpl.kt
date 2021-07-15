/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GitRepoImpl.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:53.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.editor.git.repo

import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import me.sungbin.gitmessengerbot.activity.main.editor.git.GitService
import me.sungbin.gitmessengerbot.activity.main.editor.git.model.GitFile
import me.sungbin.gitmessengerbot.activity.main.editor.git.model.Repo
import me.sungbin.gitmessengerbot.activity.main.editor.git.util.toBase64
import me.sungbin.gitmessengerbot.activity.setup.github.model.GithubData
import me.sungbin.gitmessengerbot.util.Json
import me.sungbin.gitmessengerbot.util.Storage
import me.sungbin.gitmessengerbot.util.config.StringConfig
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
                GitResult.Success(
                    buildRetrofit.getFileContent(
                        gitUser.userName,
                        repoName,
                        path,
                        branch
                    ).await()
                )
            )
        } catch (exception: Exception) {
            trySend(GitResult.Error(exception))
        }

        awaitClose { close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun createRepo(repo: Repo) = callbackFlow {
        try {
            trySend(GitResult.Success(buildRetrofit.createRepo(repo).await()))
        } catch (exception: Exception) {
            trySend(GitResult.Error(exception))
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
            trySend(GitResult.Success(response))
        } catch (exception: Exception) {
            trySend(GitResult.Error(exception))
        }

        awaitClose { close() }
    }
}
