/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [TestActivity.kt] created by Ji Sungbin on 21. 9. 5. 오후 9:50
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.test

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import io.github.jisungbin.gitmessengerbot.activity.editor.js.CommitHistoryItem
import io.github.jisungbin.gitmessengerbot.theme.MaterialTheme
import io.github.jisungbin.gitmessengerbot.ui.exception.ExceptionDialog
import kotlin.random.Random

@AndroidEntryPoint
class TestActivity : ComponentActivity() {

    private val ownerName = "gitmessengerbot"
    private val repoName = "gitmessengerbot-android"

    private val logs = mutableListOf<CommitHistoryItem>()
    private val _logs = mutableStateListOf<String>()

    private var message = mutableStateOf("Task Start.")

    private var exception = mutableStateOf<Exception?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                // GithubCommitHistoryTest()
                /*Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    ThreadTest()
                }*/
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Button(onClick = { exception.value = Exception(Random.nextInt().toString()) }) {
                        Text(text = "TEST")
                    }
                }
                ExceptionDialog(exception)
            }
        }
    }

    @Composable
    private fun ThreadTest() {
        val messageUpdate = 1000 // 보낼 메시지 값

        Text(text = message.value) // 텍스트뷰

        // 핸들러 인자로 루퍼를 지정할 수 있음.
        val handler = Handler(Looper.getMainLooper()) { // UI 수정이 필요함으로 메인루퍼 가져옴
            if (it.what == messageUpdate) { // 만약 받은 메시지의 값이 1000 이라면
                message.value = "Task Done." // 텍스트뷰 업데이트
            }
            true // 메시지를 한 번 받고 더 이상 처리할 일이 없으면 true
            // Callback@return: True if no further handling is desired
        }
        Thread { // 스레드 생성
            Thread.sleep(5000) // 오래 걸리는 작업을 표현하기 위해 5초 딜레이
            val message = handler.obtainMessage(messageUpdate) // 핸들러로 보낼 메시지를 지정
            handler.sendMessage(message) // 핸들러로 메시지를 보냄
        }.start() // 스레드 시작!
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

    /*@Composable
    private fun GithubCommitHistoryTest() {
        val vm: JsEditorViewModel = viewModel()
        var job: Job

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(_logs) { index, log ->
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

        LaunchedEffect(Unit) {
            job = launch(start = CoroutineStart.LAZY) {
                vm.getCommitHistory(ownerName = ownerName, repoName = repoName)
                    .collect { commitListResult ->
                        commitListResult.doWhen(
                            onSuccess = { _commitLists ->
                                val commitLists = _commitLists.value
                                commitLists.map { commitList ->
                                    launch {
                                        vm.getCommitContent(
                                            ownerName = ownerName,
                                            repoName = repoName,
                                            sha = commitList.sha
                                        ).collect { commitContentResult ->
                                            commitContentResult.doWhen(
                                                onSuccess = { commitContents ->
                                                    commitContents.value.forEach { commitContentItem ->
                                                        Timber.i("added: ${commitContentItem.filename}")
                                                        logs.add(
                                                            CommitHistoryItem(
                                                                key = commitList,
                                                                items = commitContentItem
                                                            )
                                                        )
                                                    }
                                                },
                                                onFail = { exception ->
                                                    Timber.e(exception)
                                                }
                                            )
                                        }
                                    }
                                }.joinAll()
                            },
                            onFail = { exception ->
                                Timber.e(exception)
                            }
                        )
                    }
            }

            job.start()
            job.join()
            Timber.i("job finish")
            _logs.addAll(logs.sortedByDescending { it.key.date }.map { it.items.filename })
        }
    }*/
}
