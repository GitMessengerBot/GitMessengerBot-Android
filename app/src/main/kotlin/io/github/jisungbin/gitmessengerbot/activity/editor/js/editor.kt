/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [editor.kt] created by Ji Sungbin on 21. 7. 10. 오전 4:41.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.editor.js

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.activity.editor.git.model.FileContentResponse
import io.github.jisungbin.gitmessengerbot.activity.editor.git.model.GitFile
import io.github.jisungbin.gitmessengerbot.activity.editor.git.model.Repo
import io.github.jisungbin.gitmessengerbot.activity.editor.git.repo.GitRepo
import io.github.jisungbin.gitmessengerbot.activity.main.script.ScriptItem
import io.github.jisungbin.gitmessengerbot.activity.main.script.ScriptLang
import io.github.jisungbin.gitmessengerbot.activity.main.script.toScriptSuffix
import io.github.jisungbin.gitmessengerbot.bot.Bot
import io.github.jisungbin.gitmessengerbot.repo.Result
import io.github.jisungbin.gitmessengerbot.theme.colors
import io.github.jisungbin.gitmessengerbot.util.Util
import io.github.jisungbin.gitmessengerbot.util.Web
import io.github.jisungbin.gitmessengerbot.util.config.StringConfig
import io.github.jisungbin.gitmessengerbot.util.extension.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.jsoup.Jsoup

@Composable
fun Editor(gitRepo: GitRepo, script: ScriptItem, scaffoldState: ScaffoldState) {
    val codeField = remember { mutableStateOf(TextFieldValue(Bot.getCode(script))) }
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
                gitRepo = gitRepo,
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
            modifier = Modifier.fillMaxSize(),
            colors = TextFieldDefaults.textFieldColors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.White
            )
        )
    }
}

@OptIn(InternalCoroutinesApi::class)
@Composable
private fun DrawerLayout(
    gitRepo: GitRepo,
    script: ScriptItem,
    codeField: MutableState<TextFieldValue>,
    undoStack: MutableState<String>
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val repoName = script.name

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
                text = stringResource(R.string.editor_drawer_git),
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
                    gitRepo.createRepo(
                        repo = Repo(
                            name = repoName,
                            description = StringConfig.GitDefaultRepoDescription
                        )
                    ).collect { result ->
                        when (result) {
                            is Result.Success<*> -> toast(
                                context,
                                context.getString(R.string.editor_toast_repo_create_success)
                            )
                            is Result.Fail -> Util.error(
                                context,
                                context.getString(
                                    R.string.editor_toast_repo_create_error,
                                    result.exception
                                )
                            )
                        }
                    }
                }
            }
        ) {
            Text(text = stringResource(R.string.editor_drawer_create_repo, script.name))
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            onClick = {
                coroutineScope.launch {
                    gitRepo.getFileContent(
                        repoName = repoName,
                        path = "script.${script.lang.toScriptSuffix()}"
                    ).collect { commitResult ->
                        when (commitResult) {
                            is Result.Success<*> -> {
                                gitRepo.updateFile(
                                    repoName = repoName,
                                    path = "script.${script.lang.toScriptSuffix()}",
                                    gitFile = GitFile(
                                        message = StringConfig.GitDefaultCommitMessage,
                                        content = codeField.value.text,
                                        sha = (commitResult.response as FileContentResponse).sha
                                    )
                                ).collect { updateResult ->
                                    when (updateResult) {
                                        is Result.Success<*> -> toast(
                                            context,
                                            context.getString(R.string.editor_toast_commit_success)
                                        )
                                        is Result.Fail -> Util.error(
                                            context,
                                            context.getString(
                                                R.string.editor_toast_commit_error,
                                                updateResult.exception
                                            )
                                        )
                                    }
                                }
                            }
                            is Result.Fail -> Util.error(
                                context,
                                context.getString(
                                    R.string.editor_toast_content_get_error,
                                    commitResult.exception
                                )
                            )
                        }
                    }
                }
            }
        ) {
            Text(text = stringResource(R.string.editor_drawer_commit_and_push))
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            onClick = {
                coroutineScope.launch {
                    gitRepo.getFileContent(
                        repoName = repoName,
                        path = "script.${script.lang.toScriptSuffix()}"
                    ).collect { fileContentResult ->
                        when (fileContentResult) {
                            is Result.Success<*> -> {
                                val contentDownloadUrl =
                                    (fileContentResult.response as FileContentResponse).downloadUrl
                                codeField.value = TextFieldValue(Web.parse(contentDownloadUrl))
                                toast(
                                    context,
                                    context.getString(R.string.editor_toast_file_update_success)
                                )
                            }
                            is Result.Fail -> Util.error(
                                context,
                                context.getString(
                                    R.string.editor_toast_content_get_error,
                                    fileContentResult.exception
                                )
                            )
                        }
                    }
                }
            }
        ) {
            Text(text = stringResource(R.string.editor_drawer_update_project))
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            onClick = {
                // todo
            }
        ) {
            Text(text = stringResource(R.string.editor_drawer_commit_history))
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
                    text = stringResource(R.string.editor_drawer_beautify),
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
                            val minify = async(Dispatchers.IO) {
                                Jsoup.connect("https://javascript-minifier.com/raw")
                                    .ignoreContentType(true)
                                    .ignoreHttpErrors(true)
                                    .data("input", codeField.value.text)
                                    .post()
                                    .wholeText()
                            }
                            codeField.value = TextFieldValue(minify.await())
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(text = stringResource(R.string.editor_drawer_minify))
                }
                OutlinedButton(
                    onClick = {
                        undoStack.value = codeField.value.text
                        coroutineScope.launch {
                            val beautify = async(Dispatchers.IO) {
                                JSONObject(
                                    Jsoup.connect("https://amp.prettifyjs.net")
                                        .ignoreContentType(true)
                                        .ignoreHttpErrors(true)
                                        .data("input", codeField.value.text)
                                        .post()
                                        .wholeText()
                                ).getString("output")
                            }
                            codeField.value = TextFieldValue(beautify.await())
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text(text = stringResource(R.string.editor_drawer_pretty))
                }
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
    undoStack: MutableState<String>
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
                    toast(context, context.getString(R.string.editor_toast_saved))
                    Bot.save(script, codeField.value.text)
                }
                .constrainAs(save) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        )
        AnimatedVisibility(visible = undoStack.value.isNotBlank()) {
            Icon(
                painter = painterResource(R.drawable.ic_round_undo_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .combinedClickable(
                        onClick = {
                            toast(
                                context,
                                context.getString(R.string.editor_toast_confirm_undo_beautify)
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
            )
        }
    }
}
