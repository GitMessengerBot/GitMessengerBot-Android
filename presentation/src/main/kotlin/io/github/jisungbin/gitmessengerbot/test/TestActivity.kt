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
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import io.github.jisungbin.gitmessengerbot.activity.editor.js.CommitHistoryItem
import io.github.jisungbin.gitmessengerbot.activity.editor.js.JsEditorViewModel
import io.github.jisungbin.gitmessengerbot.domain.github.doWhen
import io.github.jisungbin.gitmessengerbot.theme.MaterialTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class TestActivity : ComponentActivity() {

    private val ownerName = "gitmessengerbot"
    private val repoName = "gitmessengerbot-android"

    private val logs = mutableStateListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                CompositionSizeAnimationTest()
            }
        }
    }

    private fun log(message: Any) {
        Timber.i(message.toString())
        logs.add(message.toString())
    }

    @Composable
    private fun CompositionSizeAnimationTest() {
        val vm: TestViewModel = viewModel()
        val scrollState = rememberLazyListState()
        val scrollUpState by vm.scrollUp.observeAsState(false)
        val itemHeigth by animateIntAsState(if (!scrollUpState) 100 else 0)

        vm.updateScrollPosition(scrollState.firstVisibleItemIndex)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .border(width = 5.dp, color = Color.Red)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(color = Color.Blue)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(itemHeigth.dp)
                        .background(color = Color.Yellow)
                ) {
                    repeat(10) {
                        Text(text = it.toString())
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = scrollState,
                verticalArrangement = Arrangement.spacedBy(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(items = List(100) { it }) { item ->
                    Text(text = item.toString())
                }
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    private fun GithubCommitHistoryTest() {
        /*setContent {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(30.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                itemsIndexed(logs) { index, log ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
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
                        if (index < logs.size - 1) {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(30.dp)
                            )
                            Divider(modifier = Modifier.fillMaxWidth(), thickness = 5.dp)
                        }
                    }
                }
            }
            Test()
        }*/

        val vm: JsEditorViewModel = viewModel()

        rememberCoroutineScope().launch {
            vm.getCommitHistory(ownerName = ownerName, repoName = repoName)
                .collect { commitListResult ->
                    commitListResult.doWhen(
                        onSuccess = { _commitLists ->
                            val commitLists = _commitLists.value
                            log("commitListResult success: $commitLists")
                            val commitHistory = mutableListOf<CommitHistoryItem>()
                            commitLists.mapIndexed { commitListIndex, commitList ->
                                log("commitList launched($commitListIndex): $commitList")
                                async(Dispatchers.IO) {
                                    vm.getCommitContent(
                                        ownerName = ownerName,
                                        repoName = repoName,
                                        sha = commitList.sha
                                    ).collect { commitContentResult ->
                                        commitContentResult.doWhen(
                                            onSuccess = { _commitContents ->
                                                val commitContents = _commitContents.value
                                                log("commitContents result($commitListIndex): $commitContents")
                                                commitContents.mapIndexed { commitContentIndex, commitContentItem ->
                                                    log("commitContentItem launched($commitListIndex-$commitContentIndex): $commitContentItem")
                                                    async(Dispatchers.IO) {
                                                        commitHistory.add(
                                                            CommitHistoryItem(
                                                                key = commitList,
                                                                items = commitContentItem
                                                            )
                                                        )
                                                    }
                                                }.awaitAll()
                                                log("ALL TASK ENDED")
                                            },
                                            onFail = { exception ->
                                                log("ERROR: $exception")
                                            }
                                        )
                                    }
                                }
                            }.awaitAll()
                            log("ALL TASK ENDED2: $commitHistory")
                        },
                        onFail = { exception ->
                            log("ERROR2: $exception")
                        }
                    )
                }
        }
    }
}
