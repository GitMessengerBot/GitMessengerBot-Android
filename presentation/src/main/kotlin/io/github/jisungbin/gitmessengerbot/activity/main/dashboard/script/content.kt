/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [content.kt] created by Ji Sungbin on 21. 6. 19. 오후 10:56.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.main.dashboard.script

import android.content.Intent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.activity.debug.DebugActivty
import io.github.jisungbin.gitmessengerbot.activity.editor.js.JsEditorActivity
import io.github.jisungbin.gitmessengerbot.activity.main.MainViewModel
import io.github.jisungbin.gitmessengerbot.common.constant.IntentConstant
import io.github.jisungbin.gitmessengerbot.common.core.Util
import io.github.jisungbin.gitmessengerbot.common.extension.doWhen
import io.github.jisungbin.gitmessengerbot.common.extension.isEnglish
import io.github.jisungbin.gitmessengerbot.common.extension.toast
import io.github.jisungbin.gitmessengerbot.common.script.ScriptLang
import io.github.jisungbin.gitmessengerbot.common.script.toScriptLangName
import io.github.jisungbin.gitmessengerbot.theme.colors
import io.github.jisungbin.gitmessengerbot.theme.twiceLightGray
import io.github.jisungbin.gitmessengerbot.util.extension.composableActivityViewModel
import io.github.sungbin.gitmessengerbot.core.bot.Bot
import io.github.sungbin.gitmessengerbot.core.bot.script.ScriptItem
import io.github.sungbin.gitmessengerbot.core.bot.script.search
import kotlin.random.Random
import kotlinx.coroutines.launch

@Composable
fun ScriptContent() {
    val vm: MainViewModel = composableActivityViewModel()
    val searchField = remember { mutableStateOf(TextFieldValue()) }
    val scriptAddDialogVisible = remember { mutableStateOf(false) }

    ScriptAddDialog(visible = scriptAddDialogVisible)
    vm.updateFabAction {
        scriptAddDialogVisible.value = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.primary)
    ) {
        Header(searchField = searchField)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 50f, topEnd = 50f))
                .background(twiceLightGray)
                .padding(15.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 15.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_round_script_24),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.activity_main_composable_dashboard_script_content_label),
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(fontSize = 18.sp)
                )
            }
            LazyScript(
                modifier = Modifier.padding(top = 15.dp),
                search = searchField.value.text
            )
        }
    }
}

@Composable
private fun LazyScript(modifier: Modifier, search: String) { // TODO: 키보드 입력 1초동안 없을 때 검색하게 개선 필요
    val scripts = Bot.scripts.collectAsState().value.search(search)

    if (scripts.isNotEmpty()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
            contentPadding = PaddingValues(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(
                items = scripts,
                key = { script -> script.id }
            ) { script ->
                ScriptItem(script = script)
            }
        }
    } else {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.scripts_empty))

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = Modifier.size(250.dp),
                iterations = LottieConstants.IterateForever,
                composition = composition,
            )
            Text(
                text = stringResource(R.string.activity_main_composable_dashboard_script_content_empty),
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun CompileErrorDialog(visible: MutableState<Boolean>, exceptionMessage: String) {
    if (visible.value) {
        val context = LocalContext.current

        AlertDialog(
            onDismissRequest = { visible.value = false },
            buttons = {
                OutlinedButton(onClick = { Util.copy(context, exceptionMessage) }) {
                    Text(text = stringResource(R.string.activity_main_composable_dashboard_script_content_dialog_button_copy_error))
                }
            },
            title = { Text(text = stringResource(R.string.activity_main_composable_dashboard_script_content_dialog_compile_error)) },
            text = { Text(text = exceptionMessage) },
            modifier = Modifier.width(250.dp),
            shape = RoundedCornerShape(30.dp)
        )
    }
}

@Composable
private fun ScriptItem(script: ScriptItem) {
    val shape = RoundedCornerShape(20.dp)
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val compileErrorDialogVisible = remember { mutableStateOf(false) }
    var compileErrorExceptionMessage by remember { mutableStateOf("") }
    var isRotated by remember { mutableStateOf(false) }

    val angle by animateFloatAsState(
        targetValue = if (isRotated) 360F else 0F,
        animationSpec = tween(durationMillis = 2000),
        finishedListener = { isRotated = false }
    )

    CompileErrorDialog(
        visible = compileErrorDialogVisible,
        exceptionMessage = compileErrorExceptionMessage
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .shadow(elevation = 3.dp, shape = shape)
            .clip(shape)
            .clickable {
                val intent = Intent(context, JsEditorActivity::class.java).apply {
                    putExtra(IntentConstant.ScriptId, script.id)
                }
                context.startActivity(intent)
            }
            .background(
                brush = Brush.horizontalGradient(colors = listOf(colors.primary, colors.secondary)),
                shape = shape
            )
            .padding(top = 10.dp, bottom = 10.dp, start = 15.dp, end = 15.dp)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (
                compileState, reload, debug, logcat, setting,
                scriptName, scriptNameUnderline,
                scriptLangDeco, scriptLang, scriptLastRunTime, scriptPower,
            ) = createRefs()

            val compileStateShape = RoundedCornerShape(15.dp)
            val compileStateBackgroundColor by animateColorAsState(if (script.compiled) Color.White else Color.Transparent)
            val compileStateTextColor by animateColorAsState(if (script.compiled) colors.primary else Color.White)

            Text(
                text = stringResource(R.string.activity_main_composable_dashboard_script_content_compile),
                color = compileStateTextColor,
                style = TextStyle(fontSize = 13.sp),
                modifier = Modifier
                    .clip(compileStateShape)
                    .background(color = compileStateBackgroundColor, shape = compileStateShape)
                    .border(width = 1.dp, color = Color.White, shape = compileStateShape)
                    .padding(vertical = 2.dp, horizontal = 6.dp)
                    .constrainAs(compileState) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    }
            )
            Icon(
                painter = painterResource(R.drawable.ic_round_settings_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { } // TODO
                    .constrainAs(setting) {
                        end.linkTo(parent.end)
                        top.linkTo(compileState.top)
                        bottom.linkTo(compileState.bottom)
                    }
            )
            Icon(
                painter = painterResource(R.drawable.ic_round_logcat_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { } // TODO
                    .constrainAs(logcat) {
                        end.linkTo(setting.start, 16.dp)
                        top.linkTo(compileState.top)
                        bottom.linkTo(compileState.bottom)
                    }
            )
            Icon(
                painter = painterResource(R.drawable.ic_round_debug_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        val intent = Intent(context, DebugActivty::class.java).apply {
                            putExtra(IntentConstant.DebugScriptId, script.id)
                        }
                        context.startActivity(intent)
                    }
                    .constrainAs(debug) {
                        end.linkTo(logcat.start, 20.dp)
                        top.linkTo(compileState.top)
                        bottom.linkTo(compileState.bottom)
                    }
            )
            Icon(
                painter = painterResource(R.drawable.ic_round_refresh_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(25.dp)
                    .rotate(angle)
                    .clickable {
                        coroutineScope.launch {
                            isRotated = true
                            var message = ""
                            Bot
                                .compileScript(context, script)
                                .doWhen(
                                    onSuccess = {
                                        message =
                                            context.getString(R.string.activity_main_composable_dashboard_script_content_toast_success_compile)
                                    },
                                    onFailure = { exception ->
                                        compileErrorDialogVisible.value = true
                                        compileErrorExceptionMessage =
                                            exception.message.toString()
                                        message = context.getString(
                                            R.string.activity_main_composable_dashboard_script_content_toast_fail_compile,
                                            compileErrorExceptionMessage
                                        )
                                    }
                                )
                            isRotated = false
                            toast(context = context, message = message)
                        }
                    }
                    .constrainAs(reload) {
                        end.linkTo(debug.start, 18.dp)
                        top.linkTo(compileState.top)
                        bottom.linkTo(compileState.bottom)
                    }
            )
            Icon(
                painter = painterResource(R.drawable.ic_outline_circle_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .constrainAs(scriptLangDeco) {
                        start.linkTo(parent.start)
                        top.linkTo(scriptPower.top)
                        bottom.linkTo(scriptPower.bottom)
                    }
            )
            Text(
                text = script.lang.toScriptLangName(),
                color = Color.White,
                modifier = Modifier.constrainAs(scriptLang) {
                    start.linkTo(scriptLangDeco.end, 4.dp)
                    top.linkTo(scriptLangDeco.top)
                    bottom.linkTo(scriptLangDeco.bottom)
                },
                style = TextStyle(fontSize = 13.sp)
            )
            Switch(
                checked = script.power,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = colors.primaryVariant,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.Black
                ),
                onCheckedChange = {
                    script.power = it
                    Bot.scriptDataSave(script)
                },
                modifier = Modifier.constrainAs(scriptPower) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            )
            Text(
                text = script.lastRun,
                color = Color.White,
                modifier = Modifier.constrainAs(scriptLastRunTime) {
                    end.linkTo(scriptPower.start, 4.dp)
                    top.linkTo(scriptPower.top)
                    bottom.linkTo(scriptPower.bottom)
                },
                style = TextStyle(fontSize = 13.sp)
            )
            Divider(
                modifier = Modifier.constrainAs(scriptNameUnderline) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(scriptPower.top, 4.dp)
                },
                color = Color.White,
                thickness = 0.5.dp
            )
            Text(
                text = script.name,
                color = Color.White,
                modifier = Modifier.constrainAs(scriptName) {
                    top.linkTo(compileState.bottom)
                    bottom.linkTo(scriptNameUnderline.top)
                    start.linkTo(parent.start)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ScriptAddDialog(visible: MutableState<Boolean>) {
    val context = LocalContext.current
    var scriptNameField by remember { mutableStateOf(TextFieldValue()) }
    var selectedScriptLang by remember { mutableStateOf(ScriptLang.TypeScript) }
    val focusManager = LocalFocusManager.current

    if (visible.value) {
        AlertDialog(
            onDismissRequest = { visible.value = false },
            buttons = {},
            shape = RoundedCornerShape(30.dp),
            text = {
                Column(
                    modifier = Modifier
                        .width(200.dp)
                        .wrapContentHeight()
                ) {
                    Text(
                        text = stringResource(R.string.activity_main_composable_dashboard_script_content_dialog_script_nane),
                        color = Color.Black
                    )
                    TextField(
                        label = {
                            Text(
                                text = stringResource(R.string.activity_main_composable_dashboard_script_content_dialog_script_name_allow_only_english),
                                style = TextStyle(fontSize = 10.sp)
                            )
                        },
                        value = scriptNameField,
                        onValueChange = {
                            if (it.text.isEnglish()) {
                                scriptNameField = it
                            }
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(FocusRequester()),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    ) {
                        repeat(4) { scriptLang ->
                            val backgroundColor by animateColorAsState(if (selectedScriptLang == scriptLang) colors.secondary else Color.White)
                            val primaryColor by animateColorAsState(if (selectedScriptLang == scriptLang) colors.primary else Color.Gray)
                            val shape = RoundedCornerShape(10.dp)

                            ConstraintLayout(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(30.dp)
                                    .clip(shape)
                                    .background(
                                        color = backgroundColor.copy(alpha = 0.5f),
                                        shape = shape
                                    )
                                    .clickable {
                                        if (scriptLang == ScriptLang.Python) {
                                            toast(
                                                context,
                                                context.getString(R.string.activity_main_composable_dashboard_script_content_dialog_toast_cant_use_python_language)
                                            )
                                        } else {
                                            selectedScriptLang = scriptLang
                                        }
                                    }
                                    .padding(4.dp)
                            ) {
                                val (circle, name, check) = createRefs()

                                Icon(
                                    painter = painterResource(R.drawable.ic_outline_circle_24),
                                    contentDescription = null,
                                    tint = primaryColor,
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .constrainAs(circle) {
                                            top.linkTo(parent.top)
                                            bottom.linkTo(parent.bottom)
                                            start.linkTo(parent.start)
                                        }
                                )
                                Text(
                                    text = scriptLang.toScriptLangName(),
                                    color = primaryColor,
                                    style = TextStyle(fontSize = 13.sp),
                                    modifier = Modifier
                                        .constrainAs(name) {
                                            top.linkTo(parent.top)
                                            bottom.linkTo(parent.bottom)
                                            start.linkTo(circle.end, 15.dp)
                                        }
                                )
                                if (selectedScriptLang == scriptLang) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_round_check_24),
                                        contentDescription = null,
                                        tint = primaryColor,
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .constrainAs(check) {
                                                top.linkTo(parent.top)
                                                bottom.linkTo(parent.bottom)
                                                end.linkTo(parent.end)
                                            }
                                    )
                                }
                            }
                        }
                    }
                    Button(
                        onClick = {
                            val scriptName = scriptNameField.text
                            if (scriptName.isNotBlank()) {
                                val scriptItem = ScriptItem(
                                    id = Random.nextInt(),
                                    name = scriptName,
                                    power = false,
                                    lang = selectedScriptLang,
                                    compiled = false,
                                    lastRun = ""
                                )
                                Bot.addScript(scriptItem)
                                visible.value = false
                                scriptNameField = TextFieldValue()
                            } else {
                                toast(
                                    context,
                                    context.getString(R.string.activity_main_composable_dashboard_script_content_dialog_toast_confirm_script_name)
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = colors.secondary),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.activity_main_composable_dashboard_script_content_dialog_button_script_add),
                            color = Color.White
                        )
                    }
                }
            }
        )
    }
}
