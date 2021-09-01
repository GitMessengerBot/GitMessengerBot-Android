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
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubFile
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubRepo
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubCreateRepoUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubGetFileContentUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubUpdateFileUseCase
import io.github.jisungbin.gitmessengerbot.util.RequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import org.json.JSONObject
import org.jsoup.Jsoup
import javax.inject.Inject

@HiltViewModel
class JsEditorViewModel @Inject constructor(
    private val githubGetFileContentUseCase: GithubGetFileContentUseCase,
    private val githubUpdateFileUseCase: GithubUpdateFileUseCase,
    private val githubCreateRepoUseCase: GithubCreateRepoUseCase,
) : ViewModel() {

    private sealed class BeautifyType {
        object Minify : BeautifyType()
        object Pretty : BeautifyType()
    }

    private fun provideJsoup(beautifyType: BeautifyType, code: String) =
        Jsoup.connect(if (beautifyType == BeautifyType.Minify) "https://javascript-minifier.com/raw" else "https://amp.prettifyjs.net")
            .ignoreContentType(true)
            .ignoreHttpErrors(true)
            .data("input", code)
            .post()
            .wholeText()

    suspend fun githubCreateRepo(githubRepo: GithubRepo) = githubCreateRepoUseCase(githubRepo)

    suspend fun githubGetFileContent(repoName: String, path: String, branch: String) =
        githubGetFileContentUseCase(repoName, path, branch)

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun githubCommitAndPush(repoName: String, path: String, githubFile: GithubFile) =
        callbackFlow {
            githubGetFileContent(
                repoName = repoName,
                path = path,
                branch = githubFile.branch
            ).collect { fileContentResult ->
                fileContentResult.doWhen(
                    onSuccess = { fileContent ->
                        githubUpdateFileUseCase(
                            repoName,
                            path,
                            githubFile.copy(sha = fileContent.sha)
                        ).collect { updateFileResult ->
                            updateFileResult.doWhen(
                                onSuccess = {
                                    trySend(RequestResult.Success(Unit))
                                },
                                onFail = { exception ->
                                    trySend(RequestResult.Fail(exception))
                                }
                            )
                        }
                    },
                    onFail = { exception ->
                        trySend(RequestResult.Fail(exception))
                    }
                )
            }

            awaitClose { close() }
        }

    suspend fun codeMinify(code: String): String = coroutineScope {
        async(Dispatchers.IO) { provideJsoup(BeautifyType.Minify, code) }
    }.await()

    suspend fun codePretty(code: String): String = coroutineScope {
        async(Dispatchers.IO) {
            JSONObject(provideJsoup(BeautifyType.Pretty, code)).getString("output")
        }
    }.await()
}
