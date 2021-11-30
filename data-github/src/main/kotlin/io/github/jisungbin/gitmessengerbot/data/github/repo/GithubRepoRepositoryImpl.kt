/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GitRepoImpl.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:53.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.repo

import io.github.jisungbin.gitmessengerbot.common.constant.GithubConstant
import io.github.jisungbin.gitmessengerbot.common.core.Storage
import io.github.jisungbin.gitmessengerbot.common.extension.toModel
import io.github.jisungbin.gitmessengerbot.data.github.api.GithubRepoService
import io.github.jisungbin.gitmessengerbot.data.github.mapper.toDomain
import io.github.jisungbin.gitmessengerbot.data.github.util.isValid
import io.github.jisungbin.gitmessengerbot.data.github.util.toFailResult
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubFile
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubFileContent
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubRepo
import io.github.jisungbin.gitmessengerbot.domain.github.model.user.GithubData
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubRepoRepository
import kotlin.coroutines.resume
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Retrofit

class GithubRepoRepositoryImpl(retrofit: Retrofit) : GithubRepoRepository {
    private val api = retrofit.create(GithubRepoService::class.java)
    private val githubData: GithubData = Storage.read(GithubConstant.DataPath, null)?.toModel()
        ?: /*throw DataGithubException("GithubConfig.DataPath 데이터가 null 이에요.")*/ GithubData() // TODO: null 대응 (ScopedStorage 대응)

    override suspend fun getFileContent(
        repoName: String,
        path: String,
        branch: String,
        coroutineScope: CoroutineScope
    ): Result<GithubFileContent> = suspendCancellableCoroutine { continuation ->
        coroutineScope.launch {
            try {
                val request = api.getFileContent(githubData.userName, repoName, path, branch)
                continuation.resume(
                    if (request.isValid()) {
                        Result.success(request.body()!!.toDomain())
                    } else {
                        request.toFailResult("getFileContent")
                    }
                )
            } catch (exception: Exception) {
                continuation.resume(Result.failure(exception))
            }
        }
    }

    override suspend fun createRepo(
        githubRepo: GithubRepo,
        coroutineScope: CoroutineScope
    ): Result<Unit> = suspendCancellableCoroutine { continuation ->
        coroutineScope.launch {
            try {
                val request = api.createRepo(githubRepo)
                continuation.resume(
                    if (request.isValid()) {
                        Result.success(Unit)
                    } else {
                        request.toFailResult("createRepo")
                    }
                )
            } catch (exception: Exception) {
                continuation.resume(Result.failure(exception))
            }
        }
    }

    override suspend fun updateFile(
        repoName: String,
        path: String,
        githubFile: GithubFile,
        coroutineScope: CoroutineScope
    ): Result<Unit> = suspendCancellableCoroutine { continuation ->
        coroutineScope.launch {
            try {
                val request = api.updateFile(
                    owner = githubData.userName,
                    repoName = repoName,
                    path = path,
                    githubFile = githubFile
                )
                continuation.resume(
                    if (request.isValid()) {
                        Result.success(Unit)
                    } else {
                        request.toFailResult("updateFile")
                    }
                )
            } catch (exception: Exception) {
                continuation.resume(Result.failure(exception))
            }
        }
    }
}
