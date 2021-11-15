/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [dialog.kt] created by Ji Sungbin on 21. 7. 25. 오전 2:40.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.main.setting

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.common.core.Util
import io.github.jisungbin.gitmessengerbot.common.core.Web
import io.github.jisungbin.gitmessengerbot.common.extension.runIf
import io.github.jisungbin.gitmessengerbot.common.extension.toast
import io.github.jisungbin.gitmessengerbot.common.script.ScriptLang
import io.github.jisungbin.gitmessengerbot.common.script.toScriptLangName
import io.github.jisungbin.gitmessengerbot.theme.colors
import io.github.jisungbin.gitmessengerbot.theme.transparentTextFieldColors
import io.github.jisungbin.gitmessengerbot.util.extension.noRippleClickable
import io.github.sungbin.gitmessengerbot.core.bot.script.getScriptDefaultCode
import io.github.sungbin.gitmessengerbot.core.setting.AppConfig

@Composable
fun DonateDialog(visible: MutableState<Boolean>) {
    if (visible.value) {
        val context = LocalContext.current

        AlertDialog(
            onDismissRequest = { visible.value = false },
            confirmButton = {
                OutlinedButton(
                    onClick = {
                        Web.open(context, Web.Link.DonateOpenChat)
                    }
                ) {
                    Text(text = stringResource(R.string.activity_main_composable_setting_dialog_button_direct_go))
                }
                OutlinedButton(
                    onClick = {
                        Util.copy(
                            context,
                            context.getString(R.string.activity_main_composable_setting_dialog_kakaotalk_id)
                        )
                    }
                ) {
                    Text(text = stringResource(R.string.activity_main_composable_setting_dialog_button_copy_kakaotalk_id))
                }
            },
            title = {
                Text(
                    text = stringResource(R.string.activity_main_composable_setting_dialog_thanks_donate),
                    modifier = Modifier.padding(bottom = 30.dp)
                )
            }
        )
    }
}

@Composable
fun GitDefaultCommitMessageDialog(visible: MutableState<Boolean>) {
    if (visible.value) {
        var commitMessageField by remember { mutableStateOf(TextFieldValue(AppConfig.appValue.gitDefaultCommitMessage)) }

        AlertDialog(
            onDismissRequest = { visible.value = false },
            title = { Text(text = stringResource(R.string.activity_main_composable_setting_content_default_commit_message)) },
            confirmButton = {
                OutlinedButton(
                    onClick = {
                        AppConfig.update { app ->
                            app.copy(gitDefaultCommitMessage = commitMessageField.text)
                        }
                    }
                ) {
                    Text(text = stringResource(R.string.save))
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Spacer()
                    TextField(
                        value = commitMessageField,
                        onValueChange = { commitMessageField = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        colors = transparentTextFieldColors(backgroundColor = Color.White)
                    )
                }
            }
        )
    }
}

@Composable
fun GitDefaultCreateRepoOptionsDialog(visible: MutableState<Boolean>) {
    if (visible.value) {
        AlertDialog(
            onDismissRequest = { visible.value = false },
            title = { Text(text = stringResource(R.string.activity_main_composable_setting_content_default_repo_create_option)) },
            confirmButton = {
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Text(text = stringResource(R.string.save))
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Spacer()
                    // TODO
                }
            }
        )
    }
}

@Composable
fun KakaoTalkPackageNamesDialog(visible: MutableState<Boolean>) {
    if (visible.value) {
        val context = LocalContext.current
        val app by AppConfig.app.collectAsState()
        var newKakaoTalkPackage by remember { mutableStateOf(TextFieldValue()) }
        val focusManager = LocalFocusManager.current

        AlertDialog(
            onDismissRequest = { visible.value = false },
            title = { Text(text = stringResource(R.string.activity_main_composable_setting_content_kakaotalk_package_names)) },
            buttons = {},
            text = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    item {
                        TextField(
                            value = newKakaoTalkPackage,
                            onValueChange = {
                                newKakaoTalkPackage = it
                            },
                            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                            trailingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.ic_round_add_24),
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        val packageName = newKakaoTalkPackage.text
                                        if (packageName.isNotBlank()) {
                                            updateKakaoTalkPackageNames(context, packageName)
                                            newKakaoTalkPackage = TextFieldValue()
                                            focusManager.clearFocus()
                                        }
                                    }
                                )
                            },
                            modifier = Modifier
                                .padding(bottom = 15.dp)
                                .focusRequester(FocusRequester())
                        )
                    }
                    items(
                        items = app.kakaoTalkPackageNames,
                        key = { it }
                    ) { packageName ->
                        ApplicationItem(packageName = packageName)
                    }
                }
            }
        )
    }
}

private fun updateKakaoTalkPackageNames(context: Context, packageName: String) {
    AppConfig.update { app ->
        val kakaoTalkPackageNames = app.kakaoTalkPackageNames.toMutableList()
        return@update if (!kakaoTalkPackageNames.contains(packageName)) {
            kakaoTalkPackageNames.add(packageName)
            app.copy(kakaoTalkPackageNames = kakaoTalkPackageNames)
        } else {
            toast(
                context,
                context.getString(
                    R.string.activity_main_composable_setting_dialog_toast_already_added_package,
                    packageName
                )
            )
            app
        }
    }
}

@Composable
fun ScriptAddDefaultLanguageDialog(visible: MutableState<Boolean>) {
    if (visible.value) {
        val scriptLangItemShape = RoundedCornerShape(10.dp)

        AlertDialog(
            onDismissRequest = { visible.value = false },
            buttons = {},
            title = {
                Text(text = stringResource(R.string.activity_main_composable_setting_content_default_script_language))
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Spacer()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .clip(scriptLangItemShape)
                            .border(1.dp, colors.secondary, scriptLangItemShape)
                    ) {
                        repeat(4) { scriptLang ->
                            @Composable
                            fun scriptLangItemBackgroundColor(lang: Int) =
                                if (AppConfig.appValue.scriptDefaultLang == lang) colors.secondary else Color.White

                            @Composable
                            fun scriptLangItemTextColor(lang: Int) =
                                if (AppConfig.appValue.scriptDefaultLang == lang) Color.White else colors.secondary

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .background(scriptLangItemBackgroundColor(scriptLang))
                                    .noRippleClickable(onClick = {
                                        AppConfig.update { app ->
                                            app.copy(scriptDefaultLang = scriptLang)
                                        }
                                    }),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = scriptLang.toScriptLangName(),
                                    color = scriptLangItemTextColor(scriptLang),
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun ScriptAddDefaultCodeDialog(visible: MutableState<Boolean>) {
    if (visible.value) {
        val scriptLangItemShape = RoundedCornerShape(10.dp)
        val scriptDefaultCodeSettingDialogVisible = remember { mutableStateOf(false) }
        val scriptDefaultCodeLang = remember { mutableStateOf(ScriptLang.TypeScript) }

        ScriptDefaultCodeSettingDialog(
            visible = scriptDefaultCodeSettingDialogVisible,
            innerVisible = visible,
            scriptLang = scriptDefaultCodeLang.value
        )

        AlertDialog(
            onDismissRequest = { visible.value = false },
            title = { Text(text = stringResource(R.string.activity_main_composable_setting_content_default_script_cpde)) },
            buttons = {},
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Spacer()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .clip(scriptLangItemShape)
                            .border(
                                width = 1.dp,
                                color = colors.secondary,
                                shape = scriptLangItemShape
                            )
                    ) {
                        repeat(4) { scriptLang ->
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .background(Color.White)
                                    .noRippleClickable(onClick = {
                                        scriptDefaultCodeLang.value = scriptLang
                                        scriptDefaultCodeSettingDialogVisible.value = true
                                    }),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = scriptLang.toScriptLangName(),
                                    color = colors.primary,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun ScriptDefaultCodeSettingDialog(
    visible: MutableState<Boolean>,
    innerVisible: MutableState<Boolean>,
    scriptLang: Int,
) {
    if (visible.value) {
        val context = LocalContext.current
        var scriptDefaultCodeField by remember { mutableStateOf(TextFieldValue(scriptLang.getScriptDefaultCode())) }

        AlertDialog(
            onDismissRequest = { visible.value = false },
            title = {
                Text(
                    text = stringResource(
                        R.string.activity_main_composable_setting_dialog_edit_default_code,
                        scriptLang.toScriptLangName()
                    )
                )
            },
            confirmButton = {
                OutlinedButton(
                    onClick = {
                        val newCode = scriptDefaultCodeField.text
                        val scriptDefaultCode = AppConfig.appValue.scriptDefaultCode
                        when (scriptLang) {
                            ScriptLang.TypeScript -> scriptDefaultCode.ts = newCode
                            ScriptLang.JavaScript -> scriptDefaultCode.js = newCode
                            ScriptLang.Python -> scriptDefaultCode.py = newCode
                            ScriptLang.Simple -> scriptDefaultCode.sim = newCode
                        }
                        AppConfig.update { app ->
                            app.copy(scriptDefaultCode = scriptDefaultCode)
                        }
                        toast(context, context.getString(R.string.saved))
                        visible.value = false
                        innerVisible.value = false
                    }
                ) {
                    Text(text = stringResource(R.string.save))
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Spacer()
                    TextField(
                        value = scriptDefaultCodeField,
                        onValueChange = { scriptDefaultCodeField = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .runIf(AppConfig.appValue.editorHorizontalScroll) {
                                horizontalScroll(rememberScrollState())
                            },
                        colors = transparentTextFieldColors(backgroundColor = Color.White),
                    )
                }
            }
        )
    }
}

@Composable
private fun Spacer() {
    Text(text = "", modifier = Modifier.height(10.dp))
}

@Composable
private fun ApplicationItem(packageName: String) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = packageName, color = Color.Black)
        Icon(
            painter = painterResource(R.drawable.ic_round_trash_24),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    AppConfig.update { app ->
                        val kakaoTalkPackageNames = app.kakaoTalkPackageNames.toMutableList()
                        return@update if (kakaoTalkPackageNames.size == 1) {
                            toast(
                                context,
                                context.getString(R.string.activity_main_composable_setting_dialog_toast_cant_deleate_last_item)
                            )
                            app
                        } else {
                            kakaoTalkPackageNames.remove(packageName)
                            app.copy(kakaoTalkPackageNames = kakaoTalkPackageNames)
                        }
                    }
                }
        )
    }
}
