/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [setting.kt] created by Ji Sungbin on 21. 6. 19. 오후 11:54.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.main.setting

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.activity.main.script.toScriptLangName
import io.github.jisungbin.gitmessengerbot.bot.Bot
import io.github.jisungbin.gitmessengerbot.theme.colors
import io.github.jisungbin.gitmessengerbot.util.BatteryUtil
import io.github.jisungbin.gitmessengerbot.util.NotificationUtil
import io.github.jisungbin.gitmessengerbot.util.Storage
import io.github.jisungbin.gitmessengerbot.util.extension.toast

@Composable
fun Setting(activity: Activity) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = colors.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
            ) {
                Text(text = stringResource(R.string.setting_appbar_title))
            }
        },
        content = {
            SettingContent(activity)
        }
    )
}

@Suppress("NewApi")
@Composable
private fun SettingContent(activity: Activity) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        val app = Bot.app.value
        val context = LocalContext.current

        val scriptAddDefaultCodeDialogVisible = remember { mutableStateOf(false) }
        val scriptAddDefaultLanguageDialogVisible = remember { mutableStateOf(false) }
        val gitDefaultCommitMessageDialogVisible = remember { mutableStateOf(false) }
        val gitDefaultCreateRepoOptionsDialogVisible = remember { mutableStateOf(false) }
        val kakaoTalkPackageNamesDialogVisible = remember { mutableStateOf(false) }
        val openSourceLicenseVisible = remember { mutableStateOf(false) }
        val donateDialogVisble = remember { mutableStateOf(false) }

        ScriptAddDefaultCodeDialog(visible = scriptAddDefaultCodeDialogVisible)
        ScriptAddDefaultLanguageDialog(visible = scriptAddDefaultLanguageDialogVisible)
        GitDefaultCommitMessageDialog(visible = gitDefaultCommitMessageDialogVisible)
        GitDefaultCreateRepoOptionsDialog(visible = gitDefaultCreateRepoOptionsDialogVisible)
        KakaoTalkPackageNamesDialog(visible = kakaoTalkPackageNamesDialogVisible)
        OpenSourceDialog(visible = openSourceLicenseVisible)
        DonateDialog(visible = donateDialogVisble)

        Text(
            text = stringResource(R.string.setting_label_editor),
            fontSize = 18.sp,
            color = Color.Gray
        )
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.setting_editor_horizontal_scroll),
                fontSize = 15.sp,
                color = Color.Black
            )
            Switch(
                checked = app.editorHorizontalScroll.value,
                onCheckedChange = {
                    Bot.saveAndUpdate(app.copy(editorHorizontalScroll = mutableStateOf(it)))
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = colors.primaryVariant,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = colors.secondary
                ),
            )
        }
        RowContent(modifier = Modifier.padding(top = 16.dp)) {
            Text(
                text = stringResource(R.string.setting_editor_font_name),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(
                onClick = {
                    toast(
                        context,
                        context.getString(R.string.setting_toast_todo_dev)
                    )
                }
            ) {
                Text(text = app.editorFontName.value)
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.setting_editor_font_size),
                fontSize = 15.sp,
                color = Color.Black
            )
            TextField(
                value = app.editorFontSize.value.toString(),
                onValueChange = {
                    try {
                        Bot.saveAndUpdate(app.copy(editorFontSize = mutableStateOf(it.toInt())))
                    } catch (ignored: Exception) {
                    }
                },
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                modifier = Modifier
                    .wrapContentHeight()
                    .width(80.dp),
                singleLine = true
            )
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.setting_editor_auto_save),
                fontSize = 15.sp,
                color = Color.Black
            )
            TextField(
                value = app.editorAutoSave.value.toString(),
                onValueChange = {
                    try {
                        Bot.saveAndUpdate(app.copy(editorAutoSave = mutableStateOf(it.toInt())))
                    } catch (ignored: Exception) {
                    }
                },
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                modifier = Modifier
                    .wrapContentHeight()
                    .width(80.dp),
                singleLine = true
            )
        }
        Text(
            text = stringResource(R.string.setting_label_script),
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 30.dp)
        )
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.setting_script_add_default_code),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(onClick = { scriptAddDefaultCodeDialogVisible.value = true }) {
                Text(text = stringResource(R.string.setting_script_each_language_option))
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.setting_script_default_add_lang),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(onClick = { scriptAddDefaultLanguageDialogVisible.value = true }) {
                Text(text = app.scriptDefaultLang.value.toScriptLangName())
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.setting_script_default_response_function_name),
                fontSize = 15.sp,
                color = Color.Black
            )
            TextField(
                value = app.scriptResponseFunctionName.value,
                onValueChange = {
                    if (!it.contains(" ")) {
                        Bot.saveAndUpdate(app.copy(scriptResponseFunctionName = mutableStateOf(it)))
                    }
                },
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                modifier = Modifier
                    .wrapContentHeight()
                    .width(120.dp),
                singleLine = true
            )
        }
        Text(
            text = stringResource(R.string.setting_label_git),
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 30.dp)
        )
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.setting_git_default_branch),
                fontSize = 15.sp,
                color = Color.Black
            )
            TextField(
                value = app.gitDefaultBranch.value,
                onValueChange = {
                    Bot.saveAndUpdate(
                        app.copy(
                            gitDefaultBranch = mutableStateOf(
                                it.replace(
                                    " ",
                                    "-"
                                )
                            )
                        )
                    )
                },
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                modifier = Modifier
                    .wrapContentHeight()
                    .width(120.dp),
                singleLine = true
            )
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.setting_git_default_commit_message),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(onClick = { gitDefaultCommitMessageDialogVisible.value = true }) {
                Text(text = stringResource(R.string.setting_button_setting))
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.setting_git_default_new_repo_options),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(onClick = { gitDefaultCreateRepoOptionsDialogVisible.value = true }) {
                Text(text = stringResource(R.string.setting_button_setting))
            }
        }
        Text(
            text = stringResource(R.string.setting_label_app),
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 30.dp)
        )
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.setting_app_kakaotak_package_names),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(onClick = { kakaoTalkPackageNamesDialogVisible.value = true }) {
                Text(text = stringResource(R.string.setting_button_setting))
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.setting_app_notification_read_permission),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(onClick = { NotificationUtil.requestReadPermission(activity) }) {
                Text(text = stringResource(R.string.setting_button_setting))
            }
        }
        if (Storage.isScoped) {
            RowContent(modifier = Modifier.padding(top = 8.dp)) {
                Text(
                    text = stringResource(R.string.setting_app_access_storage_manager_permission),
                    fontSize = 15.sp,
                    color = Color.Black
                )
                OutlinedButton(onClick = { Storage.requestStorageManagePermission(activity) }) {
                    Text(text = stringResource(R.string.setting_button_setting))
                }
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.setting_app_ignore_battery_optimization),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(onClick = { BatteryUtil.requestIgnoreBatteryOptimization(context) }) {
                Text(text = stringResource(R.string.setting_button_setting))
            }
        }
        Text(
            text = stringResource(R.string.setting_label_etc),
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 30.dp)
        )
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.setting_etc_opensource_license),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(onClick = { openSourceLicenseVisible.value = true }) {
                Text(text = stringResource(R.string.setting_button_show))
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.setting_etc_donate),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(onClick = { donateDialogVisble.value = true }) {
                Text(text = stringResource(R.string.setting_button_thanks))
            }
        }
        Text(
            text = stringResource(
                R.string.setting_etc_lovers,
                stringResource(R.string.setting_etc_empty)
            ),
            modifier = Modifier.padding(top = 15.dp),
            color = colors.primary
        )
    }
}

@Composable
private fun RowContent(modifier: Modifier, content: @Composable () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}