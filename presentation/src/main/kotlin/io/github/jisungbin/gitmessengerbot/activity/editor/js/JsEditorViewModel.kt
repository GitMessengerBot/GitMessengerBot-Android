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
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jisungbin.gitmessengerbot.activity.editor.js.mvi.MviJsEditorSideEffect
import io.github.jisungbin.gitmessengerbot.activity.editor.js.mvi.MviJsEditorState
import io.github.jisungbin.gitmessengerbot.activity.editor.js.mvi.MviJsEditorSuccessType
import io.github.jisungbin.gitmessengerbot.common.core.Web
import io.github.jisungbin.gitmessengerbot.common.extension.doWhen
import io.github.jisungbin.gitmessengerbot.common.extension.toException
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubFile
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubRepo
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GetCommitContentUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GetCommitHistoryUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubCreateRepoUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubGetFileContentUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubUpdateFileUseCase
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class JsEditorViewModel @Inject constructor(
    private val githubGetFileContentUseCase: GithubGetFileContentUseCase,
    private val githubUpdateFileUseCase: GithubUpdateFileUseCase,
    private val githubCreateRepoUseCase: GithubCreateRepoUseCase,
    private val getCommitHistoryUseCase: GetCommitHistoryUseCase,
    private val getCommitContentUseCase: GetCommitContentUseCase,
) : ContainerHost<MviJsEditorState, MviJsEditorSideEffect>, ViewModel() {

    override val container = container<MviJsEditorState, MviJsEditorSideEffect>(MviJsEditorState())

    private enum class BeautifyType(val apiAddress: String) {
        Minify("https://javascript-minifier.com/raw"),
        Pretty("https://amp.prettifyjs.net")
    }

    private suspend fun codeBeautify(beautifyType: BeautifyType, code: String) = coroutineScope {
        async(Dispatchers.IO) {
            Jsoup.connect(beautifyType.apiAddress)
                .ignoreContentType(true)
                .ignoreHttpErrors(true)
                .data("input", code)
                .post()
                .wholeText()
        }
    }.await()

    fun githubCreateRepo(
        path: String,
        githubRepo: GithubRepo,
        githubFile: GithubFile,
    ) = intent {
        githubCreateRepoUseCase(
            githubRepoarameter = githubRepo,
            coroutineScope = viewModelScope
        ).doWhen(
            onSuccess = {
                githubUpdateFileUseCase(
                    repoName = githubRepo.name,
                    path = path,
                    githubFile = githubFile,
                    coroutineScope = viewModelScope
                ).doWhen(
                    onSuccess = {
                        reduce {
                            state.copy(
                                loaded = true,
                                exception = null,
                                successType = MviJsEditorSuccessType.GithubCreateRepo
                            )
                        }
                    },
                    onFailure = { throwable ->
                        reduce {
                            state.copy(exception = throwable.toException())
                        }
                    }
                )
            },
            onFailure = { throwable ->
                reduce {
                    state.copy(exception = throwable.toException())
                }
            }
        )
    }

    fun githubUpdateProject(repoName: String, path: String, branch: String) = intent {
        githubGetFileContentUseCase(
            repoName = repoName,
            path = path,
            branch = branch,
            coroutineScope = viewModelScope
        ).doWhen(
            onSuccess = { fileContent ->
                postSideEffect(MviJsEditorSideEffect.UpdateCodeField(Web.parse(fileContent.downloadUrl)))
                reduce {
                    state.copy(
                        loaded = true,
                        exception = null,
                        successType = MviJsEditorSuccessType.GithubUpdateProject
                    )
                }
            },
            onFailure = { throwable ->
                reduce {
                    state.copy(exception = throwable.toException())
                }
            }
        )
    }

    fun githubCommitAndPush(repoName: String, path: String, githubFile: GithubFile) = intent {
        githubGetFileContentUseCase(
            repoName = repoName,
            path = path,
            branch = githubFile.branch,
            coroutineScope = viewModelScope
        ).doWhen(
            onSuccess = { fileContent ->
                githubUpdateFileUseCase(
                    repoName = repoName,
                    path = path,
                    githubFile = githubFile.copy(sha = fileContent.sha),
                    coroutineScope = viewModelScope
                ).doWhen(
                    onSuccess = {
                        reduce {
                            state.copy(
                                loaded = true,
                                exception = null,
                                successType = MviJsEditorSuccessType.GithubCommitAndPush
                            )
                        }
                    },
                    onFailure = { throwable ->
                        reduce {
                            state.copy(exception = throwable.toException())
                        }
                    }
                )
            },
            onFailure = { throwable ->
                reduce {
                    state.copy(exception = throwable.toException())
                }
            }
        )
    }

    fun loadCommitHistory(userName: String, repoName: String) = intent {
        val commitHistory = mutableListOf<CommitHistoryItem>()

        coroutineScope {
            launch {
                getCommitHistoryUseCase(
                    owner = userName,
                    repoName = repoName,
                    coroutineScope = viewModelScope
                ).doWhen(
                    onSuccess = { commitLists ->
                        commitLists.value.forEach { commitList ->
                            getCommitContentUseCase(
                                owner = userName,
                                repoName = repoName,
                                sha = commitList.sha,
                                coroutineScope = viewModelScope
                            ).doWhen(
                                onSuccess = { commitContents ->
                                    commitContents.value.forEach { commitContentItem ->
                                        commitHistory.add(
                                            CommitHistoryItem(
                                                key = commitList,
                                                items = commitContentItem
                                            )
                                        )
                                    }
                                },
                                onFailure = { throwable ->
                                    reduce {
                                        state.copy(exception = throwable.toException())
                                    }
                                }
                            )
                        }
                    },
                    onFailure = { throwable ->
                        reduce {
                            state.copy(exception = throwable.toException())
                        }
                    }
                )
            }.join()
            postSideEffect(MviJsEditorSideEffect.UpdateCommitHistoryItems(commitHistory))
            reduce {
                state.copy(
                    loaded = true,
                    exception = null,
                    successType = MviJsEditorSuccessType.None
                )
            }
        }
    }

    fun codeMinify(code: String) = intent {
        postSideEffect(
            MviJsEditorSideEffect.UpdateCodeField(
                codeBeautify(
                    beautifyType = BeautifyType.Minify,
                    code = code
                )
            )
        )
        reduce {
            state.copy(loaded = true, exception = null, successType = MviJsEditorSuccessType.None)
        }
    }

    fun codePretty(code: String) = intent {
        postSideEffect(
            MviJsEditorSideEffect.UpdateCodeField(
                codeBeautify(
                    beautifyType = BeautifyType.Pretty,
                    code = code
                )
            )
        )
        reduce {
            state.copy(loaded = true, exception = null, successType = MviJsEditorSuccessType.None)
        }
    }
}
