/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [composable_setting.kt] created by Ji Sungbin on 21. 6. 19. 오후 11:54.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.home.composable_setting

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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.activity.home.setting.DonateDialog
import io.github.jisungbin.gitmessengerbot.activity.home.setting.GitDefaultCommitMessageDialog
import io.github.jisungbin.gitmessengerbot.activity.home.setting.GitDefaultCreateRepoOptionsDialog
import io.github.jisungbin.gitmessengerbot.activity.home.setting.KakaoTalkPackageNamesDialog
import io.github.jisungbin.gitmessengerbot.activity.home.setting.OpenSourceDialog
import io.github.jisungbin.gitmessengerbot.activity.home.setting.ScriptAddDefaultCodeDialog
import io.github.jisungbin.gitmessengerbot.activity.home.setting.ScriptAddDefaultLanguageDialog
import io.github.jisungbin.gitmessengerbot.theme.colors
import io.github.jisungbin.gitmessengerbot.common.config.Config
import io.github.jisungbin.gitmessengerbot.common.core.BatteryUtil
import io.github.jisungbin.gitmessengerbot.common.core.NotificationUtil
import io.github.jisungbin.gitmessengerbot.common.core.Storage
import io.github.jisungbin.gitmessengerbot.common.exception.PresentationException
import io.github.jisungbin.gitmessengerbot.common.extension.toast
import io.github.jisungbin.gitmessengerbot.common.script.toScriptLangName
import io.github.sungbin.gitmessengerbot.core.setting.AppConfig

@Composable
fun Setting(activity: Activity) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Toolbar() },
        content = { Content(activity) }
    )
}

@Composable
private fun Toolbar() {
    TopAppBar(
        backgroundColor = colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
    ) {
        Text(text = stringResource(R.string.composable_setting_appbar_title))
    }
}

@Suppress("NewApi")
@Composable
private fun Content(activity: Activity) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        val app = AppConfig.app.observeAsState().value
            ?: throw PresentationException("AppConfig.app is not loaded. (AppConfig.app value is null)")

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
            text = stringResource(R.string.composable_setting_label_editor),
            fontSize = 18.sp,
            color = Color.Gray
        )
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.composable_setting_editor_horizontal_scroll),
                fontSize = 15.sp,
                color = Color.Black
            )
            Switch(
                checked = app.editorHorizontalScroll,
                onCheckedChange = { useEditorHorizontalScroll ->
                    AppConfig.update { app ->
                        app.copy(editorHorizontalScroll = useEditorHorizontalScroll)
                    }
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
                text = stringResource(R.string.composable_setting_editor_font_name),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(
                onClick = {
                    toast(
                        context,
                        context.getString(R.string.composable_setting_toast_todo_dev)
                    )
                }
            ) {
                Text(text = app.editorFontName)
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.composable_setting_editor_font_size),
                fontSize = 15.sp,
                color = Color.Black
            )
            TextField(
                value = app.editorFontSize.toString(),
                onValueChange = { fontSize ->
                    if (fontSize.length < 3) {
                        AppConfig.update { app ->
                            app.copy(editorFontSize = fontSize.toInt())
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                modifier = Modifier
                    .wrapContentHeight()
                    .width(80.dp),
                singleLine = true
            )
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.composable_setting_editor_auto_save),
                fontSize = 15.sp,
                color = Color.Black
            )
            TextField(
                value = app.editorAutoSave.toString(),
                onValueChange = { editorAutoSave ->
                    if (editorAutoSave.length < 4) {
                        AppConfig.update { app ->
                            app.copy(editorAutoSave = editorAutoSave.toInt())
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                modifier = Modifier
                    .wrapContentHeight()
                    .width(80.dp),
                singleLine = true
            )
        }
        Text(
            text = stringResource(R.string.composable_setting_label_script),
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 30.dp)
        )
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.composable_setting_script_add_default_code),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(onClick = { scriptAddDefaultCodeDialogVisible.value = true }) {
                Text(text = stringResource(R.string.composable_setting_script_each_language_option))
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.composable_setting_script_default_add_lang),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(onClick = { scriptAddDefaultLanguageDialogVisible.value = true }) {
                Text(text = app.scriptDefaultLang.toScriptLangName())
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.composable_setting_script_default_response_function_name),
                fontSize = 15.sp,
                color = Color.Black
            )
            TextField(
                value = app.scriptResponseFunctionName,
                onValueChange = { scriptResponseFunctionName ->
                    if (!scriptResponseFunctionName.contains(" ")) {
                        AppConfig.update { app ->
                            app.copy(scriptResponseFunctionName = scriptResponseFunctionName)
                        }
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
            text = stringResource(R.string.composable_setting_label_git),
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 30.dp)
        )
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.composable_setting_git_default_branch),
                fontSize = 15.sp,
                color = Color.Black
            )
            TextField(
                value = app.gitDefaultBranch,
                onValueChange = { gitDefaultBranch ->
                    if (!gitDefaultBranch.contains(" ")) {
                        AppConfig.update { app ->
                            app.copy(gitDefaultBranch = gitDefaultBranch)
                        }
                    }
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
                text = stringResource(R.string.composable_setting_git_default_commit_message),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(onClick = { gitDefaultCommitMessageDialogVisible.value = true }) {
                Text(text = app.gitDefaultCommitMessage)
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.composable_setting_git_default_new_repo_options),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(onClick = { gitDefaultCreateRepoOptionsDialogVisible.value = true }) {
                Text(text = "TODO") // TODO
            }
        }
        Text(
            text = stringResource(R.string.composable_setting_label_app),
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 30.dp)
        )
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.composable_setting_app_kakaotak_package_names),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(onClick = { kakaoTalkPackageNamesDialogVisible.value = true }) {
                val kakaoTalkPackageNames =
                    app.kakaoTalkPackageNames.sortedBy { it == Config.KakaoTalkDefaultPackageName }
                val message =
                    if (kakaoTalkPackageNames.size == 1) Config.KakaoTalkDefaultPackageName
                    else "${kakaoTalkPackageNames.first()} 외 ${kakaoTalkPackageNames.size - 1}개 추가됨"
                Text(text = message)
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.composable_setting_app_notification_read_permission),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(onClick = {
                NotificationUtil.requestNotificationListenerPermission(activity)
            }) {
                val isPermissionGranted =
                    NotificationUtil.checkNotificationListenerPermissionGranted(context)
                val message =
                    if (isPermissionGranted) stringResource(R.string.composable_setting_button_granted)
                    else stringResource(R.string.composable_setting_button_denied)
                Text(text = message)
            }
        }
        if (Storage.isScoped) {
            RowContent(modifier = Modifier.padding(top = 8.dp)) {
                Text(
                    text = stringResource(R.string.composable_setting_app_access_storage_manager_permission),
                    fontSize = 15.sp,
                    color = Color.Black
                )
                OutlinedButton(onClick = { Storage.requestStorageManagePermission(activity) }) {
                    Text(text = stringResource(R.string.composable_setting_button_composable_setting))
                }
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.composable_setting_app_ignore_battery_optimization),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(onClick = { BatteryUtil.requestIgnoreBatteryOptimization(context) }) {
                Text(text = stringResource(R.string.composable_setting_button_composable_setting))
            }
        }
        Text(
            text = stringResource(R.string.composable_setting_label_etc),
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 30.dp)
        )
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.composable_setting_etc_opensource_license),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(onClick = { openSourceLicenseVisible.value = true }) {
                Text(text = stringResource(R.string.composable_setting_button_show))
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.composable_setting_etc_donate),
                fontSize = 15.sp,
                color = Color.Black
            )
            OutlinedButton(onClick = { donateDialogVisble.value = true }) {
                Text(text = stringResource(R.string.composable_setting_button_thanks))
            }
        }
        Text(
            text = stringResource(
                R.string.composable_setting_etc_lovers,
                "클라이드님" // todo: firebase realtime update
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
        verticalAlignment = Alignment.CenterVertically,
        content = { content() }
    )
}
