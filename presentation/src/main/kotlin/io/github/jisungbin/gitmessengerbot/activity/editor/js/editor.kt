/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [composable_editor.kt] created by Ji Sungbin on 21. 7. 10. 오전 4:41.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.editor.js

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.common.config.GithubConfig
import io.github.jisungbin.gitmessengerbot.common.core.Storage
import io.github.jisungbin.gitmessengerbot.common.core.Web
import io.github.jisungbin.gitmessengerbot.common.exception.PresentationException
import io.github.jisungbin.gitmessengerbot.common.extension.runIf
import io.github.jisungbin.gitmessengerbot.common.extension.toBase64
import io.github.jisungbin.gitmessengerbot.common.extension.toModel
import io.github.jisungbin.gitmessengerbot.common.extension.toast
import io.github.jisungbin.gitmessengerbot.common.script.ScriptLang
import io.github.jisungbin.gitmessengerbot.common.script.getScriptSuffix
import io.github.jisungbin.gitmessengerbot.domain.github.doWhen
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubFile
import io.github.jisungbin.gitmessengerbot.domain.github.model.repo.GithubRepo
import io.github.jisungbin.gitmessengerbot.domain.github.model.user.GithubData
import io.github.jisungbin.gitmessengerbot.theme.colors
import io.github.jisungbin.gitmessengerbot.theme.transparentTextFieldColors
import io.github.jisungbin.gitmessengerbot.ui.exception.showExceptionDialog
import io.github.jisungbin.gitmessengerbot.util.doWhen
import io.github.sungbin.gitmessengerbot.core.bot.Bot
import io.github.sungbin.gitmessengerbot.core.bot.script.ScriptItem
import io.github.sungbin.gitmessengerbot.core.setting.AppConfig
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

private sealed class CommitListVisible {
    object Hide : CommitListVisible()
    object Loading : CommitListVisible()
    data class Show(val history: List<CommitHistoryItem>) : CommitListVisible()

    val visible get() = this is Show
}

@Composable
fun Editor(script: ScriptItem, scaffoldState: ScaffoldState) {
    val codeField = remember { mutableStateOf(TextFieldValue(script.getCode())) }
    val undoStack = remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ToolBar(
                script = script,
                codeField = codeField,
                scaffoldState = scaffoldState,
                undoStack = undoStack
            )
        },
        scaffoldState = scaffoldState,
        drawerContent = {
            DrawerLayout(
                script = script,
                codeField = codeField,
                undoStack = undoStack
            )
        },
        drawerShape = RoundedCornerShape(topEnd = 30.dp, bottomEnd = 30.dp)
    ) {
        TextField(
            value = codeField.value,
            onValueChange = { codeField.value = it },
            modifier = Modifier
                .fillMaxSize()
                .runIf(AppConfig.appValue.editorHorizontalScroll) {
                    horizontalScroll(rememberScrollState())
                },
            colors = transparentTextFieldColors(backgroundColor = Color.White),
            textStyle = TextStyle(fontFamily = FontFamily(Font(R.font.d2coding)))
        )
    }
}

@OptIn(InternalCoroutinesApi::class)
@Composable
private fun DrawerLayout(
    script: ScriptItem,
    codeField: MutableState<TextFieldValue>,
    undoStack: MutableState<String>,
) {
    val vm: JsEditorViewModel = viewModel()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val repoName = script.name
    val gitUser: GithubData = Storage.read(GithubConfig.DataPath, null)?.toModel()
        ?: throw PresentationException("GithubConfig.DataPath data value is null.")
    val repoPath = "script.${script.lang.getScriptSuffix()}"
    val repoDescription = GithubConfig.DefaultRepoDescription // TODO
    val repoBranch = AppConfig.appValue.gitDefaultBranch // TODO
    val repo = GithubRepo(name = repoName, description = repoDescription)

    val commitHistory = remember { mutableListOf<CommitHistoryItem>() }
    var commitListVisible by remember { mutableStateOf<CommitListVisible>(CommitListVisible.Hide) }
    val commitMessage = AppConfig.appValue.gitDefaultCommitMessage // TODO
    val githubFile = GithubFile(
        message = commitMessage,
        content = codeField.value.text.toBase64(),
        sha = "",
        branch = repoBranch
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(R.drawable.ic_round_code_24),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.composable_editor_drawer_git),
                fontSize = 30.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            onClick = {
                coroutineScope.launch {
                    vm.githubCreateRepo(
                        path = repoPath,
                        githubRepo = repo,
                        githubFile = githubFile
                    ).collect { createRepoResult ->
                        createRepoResult.doWhen(
                            onSuccess = {
                                toast(
                                    context,
                                    context.getString(R.string.composable_editor_toast_repo_create_success)
                                )
                            },
                            onFail = { exception ->
                                showExceptionDialog(exception)
                            }
                        )
                    }
                }
            }
        ) {
            Text(text = stringResource(R.string.composable_editor_drawer_create_repo, script.name))
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            onClick = {
                coroutineScope.launch {
                    vm.githubCommitAndPush(
                        repoName = repoName,
                        path = repoPath,
                        githubFile = githubFile
                    ).collect { commitAndPushResult ->
                        commitAndPushResult.doWhen(
                            onSuccess = {
                                toast(
                                    context,
                                    context.getString(R.string.composable_editor_toast_commit_success)
                                )
                            },
                            onFail = { exception ->
                                showExceptionDialog(exception)
                            }
                        )
                    }
                }
            }
        ) {
            Text(text = stringResource(R.string.composable_editor_drawer_commit_and_push))
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            onClick = {
                coroutineScope.launch {
                    vm.githubGetFileContent(
                        repoName = repoName,
                        path = repoPath,
                        branch = repoBranch
                    ).collect { fileContentResult ->
                        fileContentResult.doWhen(
                            onSuccess = { fileContent ->
                                codeField.value = TextFieldValue(Web.parse(fileContent.downloadUrl))
                                toast(
                                    context,
                                    context.getString(R.string.composable_editor_toast_file_update_success)
                                )
                            },
                            onFail = { exception ->
                                showExceptionDialog(exception)
                            }
                        )
                    }
                }
            }
        ) {
            Text(text = stringResource(R.string.composable_editor_drawer_update_project))
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            onClick = {
                if (commitListVisible.visible) {
                    commitListVisible = CommitListVisible.Hide
                } else {
                    if (commitHistory.isEmpty()) {
                        commitListVisible = CommitListVisible.Loading
                        coroutineScope.launch {
                            launch {
                                vm.getCommitHistory(
                                    ownerName = gitUser.userName,
                                    repoName = repoName
                                ).collect { commitListResult ->
                                    commitListResult.doWhen(
                                        onSuccess = { commitLists ->
                                            commitLists.value.forEach { commitList ->
                                                vm.getCommitContent(
                                                    ownerName = gitUser.userName,
                                                    repoName = repoName,
                                                    sha = commitList.sha
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
                                                            Timber.e("ERROR: $exception")
                                                        }
                                                    )
                                                }
                                            }
                                        },
                                        onFail = { exception ->
                                            Timber.e("ERROR2: $exception")
                                        }
                                    )
                                }
                            }.join()
                            Timber.i("finished.")
                            commitListVisible = CommitListVisible.Show(commitHistory)
                        }
                    } else {
                        commitListVisible = CommitListVisible.Show(commitHistory)
                    }
                }
            }
        ) {
            Text(text = stringResource(R.string.composable_editor_drawer_commit_history))
        }
        Crossfade(commitListVisible) { commitHistoryLoading ->
            when (commitHistoryLoading) {
                is CommitListVisible.Hide -> Unit
                is CommitListVisible.Loading -> {
                    val composition by rememberLottieComposition(
                        LottieCompositionSpec.RawRes(
                            R.raw.loading
                        )
                    )
                    LottieAnimation(
                        iterations = LottieConstants.IterateForever,
                        composition = composition,
                        modifier = Modifier.size(200.dp)
                    )
                }
                is CommitListVisible.Show -> {
                    CommitList(
                        modifier = Modifier.fillMaxSize(),
                        items = commitHistoryLoading.history
                    )
                }
            }
        }
    }
    if (script.lang == ScriptLang.JavaScript) {
        Row(
            modifier = Modifier.padding(top = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_auto_awesome_24),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.composable_editor_drawer_beautify),
                modifier = Modifier.padding(start = 10.dp),
                fontSize = 30.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            OutlinedButton(
                onClick = {
                    undoStack.value = codeField.value.text
                    coroutineScope.launch {
                        val minifyCode = vm.codeMinify(codeField.value.text)
                        codeField.value = TextFieldValue(minifyCode)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(text = stringResource(R.string.composable_editor_drawer_minify))
            }
            OutlinedButton(
                onClick = {
                    undoStack.value = codeField.value.text
                    coroutineScope.launch {
                        val prettyCode = vm.codePretty(codeField.value.text)
                        codeField.value = TextFieldValue(prettyCode)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(text = stringResource(R.string.composable_editor_drawer_pretty))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
private fun ToolBar(
    script: ScriptItem,
    codeField: MutableState<TextFieldValue>,
    scaffoldState: ScaffoldState,
    undoStack: MutableState<String>,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = colors.primary)
            .padding(top = 10.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        val (menu, title, undo, save, reload) = createRefs()

        Icon(
            painter = painterResource(R.drawable.ic_round_menu_24),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .clickable {
                    coroutineScope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
                .constrainAs(menu) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
        )
        Text(
            text = script.name,
            color = Color.White,
            textAlign = TextAlign.Start,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(menu.end, 10.dp)
                end.linkTo(reload.start, 10.dp)
                top.linkTo(menu.top)
                bottom.linkTo(menu.bottom)
                width = Dimension.fillToConstraints
            }
        )
        Icon(
            painter = painterResource(R.drawable.ic_round_save_24),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .clickable {
                    toast(
                        context,
                        context.getString(R.string.composable_editor_toast_saved)
                    )
                    Bot.scriptCodeSave(script, codeField.value.text)
                }
                .constrainAs(save) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        )
        AnimatedVisibility(
            visible = undoStack.value.isNotBlank(),
            modifier = Modifier
                .combinedClickable(
                    onClick = {
                        toast(
                            context,
                            context.getString(R.string.composable_editor_toast_confirm_undo_beautify)
                        )
                    },
                    onLongClick = {
                        codeField.value = TextFieldValue(undoStack.value)
                        undoStack.value = ""
                    }
                )
                .constrainAs(undo) {
                    end.linkTo(save.start, 16.dp)
                    top.linkTo(save.top)
                    bottom.linkTo(save.bottom)
                }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_round_undo_24),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}
