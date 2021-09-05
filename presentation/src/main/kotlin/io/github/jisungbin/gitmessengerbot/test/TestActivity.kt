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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestActivity : ComponentActivity() {

    private val repoName = "test"
    private val gitUser: GithubData = Storage.read(GithubConfig.DataPath, null)?.toModel()
        ?: throw PresentationException("GithubConfig.DataPath data value is null.")
    private val repoPath = "script.ts"
    private val repoDescription = GithubConfig.DefaultRepoDescription
    private val repoBranch = AppConfig.appValue.gitDefaultBranch
    private val repo = GithubRepo(name = repoName, description = repoDescription)

    private val logs = mutableStateListOf("TestActivity")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(30.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(logs) { log ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Divider(modifier = Modifier.fillMaxWidth(), thickness = 5.dp)
                        Text(
                            text = with(AnnotatedString.Builder(log)) {
                                addStyle(
                                    SpanStyle(fontWeight = FontWeight.Bold),
                                    0,
                                    log.indexOf(":") + 1
                                )
                                toAnnotatedString()
                            },
                            color = Color.Black,
                            fontSize = 15.sp
                        )
                        Divider(modifier = Modifier.fillMaxWidth(), thickness = 5.dp)
                    }
                }
            }
            Test()
        }
    }

    private fun log(message: Any) {
        logs.add(message.toString())
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    private fun Test() {
        val vm: JsEditorViewModel = viewModel()
        val coroutineScope = rememberCoroutineScope()

        coroutineScope.launch {
            vm.getCommitHistory(ownerName = gitUser.userName, repoName = repoName)
                .collect { commitListResult ->
                    commitListResult.doWhen(
                        onSuccess = { _commitLists ->
                            val commitLists = _commitLists.commitList
                            log("commitListResult success: $commitLists")
                            val commitHistory = mutableListOf<CommitHistoryItem>()
                            commitLists.map { commitList ->
                                log("commitList launched: $commitList")
                                async(Dispatchers.IO) {
                                    vm.getCommitContent(
                                        ownerName = gitUser.userName,
                                        repoName = repoName,
                                        sha = commitList.sha
                                    ).collect { commitContentResult ->
                                        commitContentResult.doWhen(
                                            onSuccess = { _commitContents ->
                                                val commitContents = _commitContents.files
                                                log("commitContents result: $commitContents")
                                                commitContents.map { commitContentItem ->
                                                    log("commitContentItem launched: $commitContentItem")
                                                    async(Dispatchers.IO) {
                                                        commitHistory.add(
                                                            CommitHistoryItem(
                                                                key = commitList,
                                                                items = commitContentItem
                                                            )
                                                        )
                                                    }
                                                }.awaitAll()
                                            },
                                            onFail = { exception ->
                                                log("ERROR: $exception")
                                            }
                                        )
                                    }
                                }
                            }.awaitAll()
                            log("ALL TASK ENDED: $commitHistory")
                        },
                        onFail = { exception ->
                            log("ERROR2: $exception")
                        }
                    )
                }
        }
    }
}
