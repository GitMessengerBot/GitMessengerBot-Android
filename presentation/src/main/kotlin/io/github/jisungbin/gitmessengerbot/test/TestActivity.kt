/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [TestActivity.kt] created by Ji Sungbin on 21. 9. 5. 오후 9:50
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.test

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import io.github.jisungbin.gitmessengerbot.activity.editor.js.CommitHistoryItem
import io.github.jisungbin.gitmessengerbot.activity.editor.js.JsEditorViewModel
import io.github.jisungbin.gitmessengerbot.common.config.GithubConfig
import io.github.jisungbin.gitmessengerbot.common.core.Storage
import io.github.jisungbin.gitmessengerbot.common.exception.PresentationException
import io.github.jisungbin.gitmessengerbot.common.extension.toModel
import io.github.jisungbin.gitmessengerbot.domain.github.doWhen
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubRepo
import io.github.jisungbin.gitmessengerbot.domain.github.model.user.GithubData
import io.github.sungbin.gitmessengerbot.core.setting.AppConfig
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestActivity : ComponentActivity() {

    val repoName = "test"
    val gitUser: GithubData = Storage.read(GithubConfig.DataPath, null)?.toModel()
        ?: throw PresentationException("GithubConfig.DataPath data value is null.")
    val repoPath = "script.ts"
    val repoDescription = GithubConfig.DefaultRepoDescription // TODO
    val repoBranch = AppConfig.appValue.gitDefaultBranch // TODO
    val repo = GithubRepo(name = repoName, description = repoDescription)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Text("Test Activity")
            Test()
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(InternalCoroutinesApi::class)
    @Composable
    fun Test() {
        val vm: JsEditorViewModel = viewModel()
        val coroutineScope = rememberCoroutineScope()

        coroutineScope.launch {
            vm.getCommitHistory(ownerName = gitUser.userName, repoName = repoName)
                .collect { commitListResult ->
                    commitListResult.doWhen(
                        onSuccess = { commitLists ->
                            println("commitListResult success: ")
                            println(commitLists.commitList)
                            val commitHistory = mutableListOf<CommitHistoryItem>()
                            commitLists.commitList.forEach { commitList ->
                                println("forEach: $commitList")
                                vm.getCommitContent(
                                    ownerName = gitUser.userName,
                                    repoName = repoName,
                                    sha = commitList.sha
                                ).collect { commitContentResult ->
                                    commitContentResult.doWhen(
                                        onSuccess = { commitContents ->
                                            println("commitContents result: $commitContents")
                                            commitContents.files.forEach { commitContentItem ->
                                                println("innerForEach: $commitContentItem")
                                                commitHistory.add(
                                                    CommitHistoryItem(
                                                        key = commitList,
                                                        items = commitContentItem
                                                    )
                                                )
                                            }
                                            println("TASK ENDED")
                                        },
                                        onFail = { exception ->
                                            println("ERROR")
                                            println(exception)
                                        }
                                    )
                                }
                            }
                        },
                        onFail = { exception ->
                            println(exception)
                            println("ERROR")
                        }
                    )
                }
        }
    }
}
