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
import io.github.jisungbin.gitmessengerbot.activity.editor.js.mvi.MviJsEditorSideEffect
import io.github.jisungbin.gitmessengerbot.activity.editor.js.mvi.MviJsEditorState
import io.github.jisungbin.gitmessengerbot.activity.editor.js.mvi.MviJsEditorSuccessType
import io.github.jisungbin.gitmessengerbot.common.core.Web
import io.github.jisungbin.gitmessengerbot.domain.github.doWhen
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONObject
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

    private fun codeBeautify(beautifyType: BeautifyType, code: String) =
        Jsoup.connect(beautifyType.apiAddress)
            .ignoreContentType(true)
            .ignoreHttpErrors(true)
            .data("input", code)
            .post()
            .wholeText()

    fun githubCreateRepo(
        path: String,
        githubRepo: GithubRepo,
        githubFile: GithubFile,
    ) = intent {
        githubCreateRepoUseCase(githubRepo).collect { repoCreateResult ->
            repoCreateResult.doWhen(
                onSuccess = {
                    githubUpdateFileUseCase(
                        githubRepo.name,
                        path,
                        githubFile
                    ).collect { updateFileResult ->
                        updateFileResult.doWhen(
                            onSuccess = {
                                reduce {
                                    state.copy(
                                        loaded = true,
                                        exception = null,
                                        successType = MviJsEditorSuccessType.GithubCreateRepo
                                    )
                                }
                            },
                            onFail = { exception ->
                                reduce {
                                    state.copy(exception = exception)
                                }
                            }
                        )
                    }
                },
                onFail = { exception ->
                    reduce {
                        state.copy(exception = exception)
                    }
                }
            )
        }
    }

    fun githubUpdateProject(repoName: String, path: String, branch: String) = intent {
        githubGetFileContentUseCase(repoName, path, branch).collect { getFileContentResult ->
            getFileContentResult.doWhen(
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
                onFail = { exception ->
                    reduce {
                        state.copy(loaded = true, exception = exception)
                    }
                }
            )
        }
    }

    fun githubCommitAndPush(repoName: String, path: String, githubFile: GithubFile) = intent {
        githubGetFileContentUseCase(
            repoName,
            path,
            githubFile.branch
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
                                reduce {
                                    state.copy(
                                        loaded = true,
                                        exception = null,
                                        successType = MviJsEditorSuccessType.GithubCommitAndPush
                                    )
                                }
                            },
                            onFail = { exception ->
                                reduce {
                                    state.copy(loaded = true, exception = exception)
                                }
                            }
                        )
                    }
                },
                onFail = { exception ->
                    reduce {
                        state.copy(loaded = true, exception = exception)
                    }
                }
            )
        }
    }

    fun loadCommitHistory(userName: String, repoName: String) = intent {
        val commitHistory = mutableListOf<CommitHistoryItem>()

        coroutineScope {
            launch {
                getCommitHistoryUseCase(userName, repoName).collect { commitListResult ->
                    commitListResult.doWhen(
                        onSuccess = { commitLists ->
                            commitLists.value.forEach { commitList ->
                                getCommitContentUseCase(
                                    userName,
                                    repoName,
                                    commitList.sha
                                ).collect { commitContentResult ->
                                    commitContentResult.doWhen(
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
                                        onFail = { exception ->
                                            reduce {
                                                state.copy(loaded = true, exception = exception)
                                            }
                                        }
                                    )
                                }
                            }
                        },
                        onFail = { exception ->
                            reduce {
                                state.copy(loaded = true, exception = exception)
                            }
                        }
                    )
                }
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
        val minifyCode = coroutineScope {
            async(Dispatchers.IO) { codeBeautify(BeautifyType.Minify, code) }
        }
        postSideEffect(MviJsEditorSideEffect.UpdateCodeField(minifyCode.await()))
        reduce {
            state.copy(loaded = true, exception = null, successType = MviJsEditorSuccessType.None)
        }
    }

    fun codePretty(code: String) = intent {
        val prettyCode = coroutineScope {
            async(Dispatchers.IO) {
                JSONObject(codeBeautify(BeautifyType.Pretty, code)).getString("output")
            }
        }
        postSideEffect(MviJsEditorSideEffect.UpdateCodeField(prettyCode.await()))
        reduce {
            state.copy(loaded = true, exception = null, successType = MviJsEditorSuccessType.None)
        }
    }
}
