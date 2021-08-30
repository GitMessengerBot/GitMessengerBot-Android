/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [JsEditorViewModel.kt] created by Ji Sungbin on 21. 8. 30. 오후 6:21
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.editor.js

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jisungbin.gitmessengerbot.domain.github.doWhen
import io.github.jisungbin.gitmessengerbot.domain.github.model.GithubFile
import io.github.jisungbin.gitmessengerbot.domain.github.model.GithubRepo
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubCreateRepoUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubGetFileContentUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubUpdateFileUseCase
import io.github.jisungbin.gitmessengerbot.util.RequestResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class JsEditorViewModel @Inject constructor(
    private val githubGetFileContentUseCase: GithubGetFileContentUseCase,
    private val githubUpdateFileUseCase: GithubUpdateFileUseCase,
    private val githubCreateRepoUseCase: GithubCreateRepoUseCase,
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun githubCreateRepo(githubRepo: GithubRepo) = callbackFlow {
        githubCreateRepoUseCase(githubRepo).collect { githubCreateRepoResult ->
            githubCreateRepoResult.doWhen(
                onSuccess = {
                    trySend(RequestResult.Success(Unit))
                },
                onFail = { exception ->
                    trySend(RequestResult.Fail(exception))
                }
            )
        }

        awaitClose { close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun githubGetFileContent(repoName: String, path: String, branch: String) = callbackFlow {
        githubGetFileContentUseCase(repoName, path, branch).collect { githubGetFileContentResult ->
            githubGetFileContentResult.doWhen(
                onSuccess = { fileContent ->
                    trySend(RequestResult.Success(fileContent))
                },
                onFail = { exception ->
                    trySend(RequestResult.Fail(exception))
                }
            )
        }

        awaitClose { close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun githubUpdateFile(repoName: String, path: String, githubFile: GithubFile) = callbackFlow {
        githubUpdateFileUseCase(repoName, path, githubFile).collect { githubUpdateFileResult ->
            githubUpdateFileResult.doWhen(
                onSuccess = {
                    trySend(RequestResult.Success(Unit))
                },
                onFail = { exception ->
                    trySend(RequestResult.Fail(exception))
                }
            )
        }

        awaitClose { close() }
    }
}
