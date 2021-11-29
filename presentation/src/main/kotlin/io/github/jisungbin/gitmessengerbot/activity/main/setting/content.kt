/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [composable_setting.kt] created by Ji Sungbin on 21. 6. 19. 오후 11:54.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.main.setting

import android.content.Intent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.common.constant.BotConstant
import io.github.jisungbin.gitmessengerbot.common.core.BatteryUtil
import io.github.jisungbin.gitmessengerbot.common.core.NotificationUtil
import io.github.jisungbin.gitmessengerbot.common.core.Storage
import io.github.jisungbin.gitmessengerbot.common.extension.toast
import io.github.jisungbin.gitmessengerbot.common.script.toScriptLangName
import io.github.jisungbin.gitmessengerbot.theme.colors
import io.github.jisungbin.gitmessengerbot.util.extension.getActivity
import io.github.jisungbin.gitmessengerbot.util.extension.shimmer
import io.github.sungbin.gitmessengerbot.core.setting.AppConfig

@Composable
fun Setting() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Toolbar() },
        content = { Content() }
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
        Text(text = stringResource(R.string.activity_main_composable_setting_content_title))
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Suppress("NewApi")
@Composable
private fun Content() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        val app by AppConfig.app.collectAsState()

        val activity = getActivity()
        val context = LocalContext.current

        val scriptAddDefaultCodeDialogVisible = remember { mutableStateOf(false) }
        val scriptAddDefaultLanguageDialogVisible = remember { mutableStateOf(false) }
        val gitDefaultCommitMessageDialogVisible = remember { mutableStateOf(false) }
        val gitDefaultCreateRepoOptionsDialogVisible = remember { mutableStateOf(false) }
        val kakaoTalkPackageNamesDialogVisible = remember { mutableStateOf(false) }
        val donateDialogVisble = remember { mutableStateOf(false) }

        ScriptAddDefaultCodeDialog(visible = scriptAddDefaultCodeDialogVisible)
        ScriptAddDefaultLanguageDialog(visible = scriptAddDefaultLanguageDialogVisible)
        GitDefaultCommitMessageDialog(visible = gitDefaultCommitMessageDialogVisible)
        GitDefaultCreateRepoOptionsDialog(visible = gitDefaultCreateRepoOptionsDialogVisible)
        KakaoTalkPackageNamesDialog(visible = kakaoTalkPackageNamesDialogVisible)
        DonateDialog(visible = donateDialogVisble)

        Text(
            text = stringResource(R.string.activity_main_composable_setting_content_editer),
            style = TextStyle(fontSize = 18.sp),
            color = Color.Gray
        )
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.activity_main_composable_setting_content_horizontal_scroll),
                style = TextStyle(fontSize = 15.sp),
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
                text = stringResource(R.string.activity_main_composable_setting_content_choice_font),
                style = TextStyle(fontSize = 15.sp),
                color = Color.Black
            )
            OutlinedButton(
                onClick = {
                    toast(
                        context,
                        context.getString(R.string.todo)
                    )
                }
            ) {
                Text(text = "TODO")
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.activity_main_composable_setting_content_font_size),
                style = TextStyle(fontSize = 15.sp),
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
                text = stringResource(R.string.activity_main_composable_setting_content_auto_save),
                style = TextStyle(fontSize = 15.sp),
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
            text = stringResource(R.string.activity_main_composable_setting_content_script),
            style = TextStyle(fontSize = 18.sp),
            color = Color.Gray,
            modifier = Modifier.padding(top = 30.dp)
        )
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.activity_main_composable_setting_content_default_script_cpde),
                style = TextStyle(fontSize = 15.sp),
                color = Color.Black
            )
            OutlinedButton(onClick = { scriptAddDefaultCodeDialogVisible.value = true }) {
                Text(text = stringResource(R.string.activity_main_composable_setting_content_button_each_language_option))
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.activity_main_composable_setting_content_default_script_language),
                style = TextStyle(fontSize = 15.sp),
                color = Color.Black
            )
            OutlinedButton(onClick = { scriptAddDefaultLanguageDialogVisible.value = true }) {
                Text(text = app.scriptDefaultLang.toScriptLangName())
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.activity_main_composable_setting_content_default_script_function_name),
                style = TextStyle(fontSize = 15.sp),
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
            text = stringResource(R.string.activity_main_composable_setting_content_git),
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 30.dp)
        )
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.activity_main_composable_setting_content_default_branch),
                style = TextStyle(fontSize = 15.sp),
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
                text = stringResource(R.string.activity_main_composable_setting_content_default_commit_message),
                style = TextStyle(fontSize = 15.sp),
                color = Color.Black
            )
            OutlinedButton(onClick = { gitDefaultCommitMessageDialogVisible.value = true }) {
                Text(
                    modifier = Modifier.requiredSizeIn(maxHeight = 30.dp, maxWidth = 150.dp),
                    text = app.gitDefaultCommitMessage,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.activity_main_composable_setting_content_default_repo_create_option),
                style = TextStyle(fontSize = 15.sp),
                color = Color.Black
            )
            OutlinedButton(onClick = { gitDefaultCreateRepoOptionsDialogVisible.value = true }) {
                Text(text = "TODO") // TODO
            }
        }
        Text(
            text = stringResource(R.string.activity_main_composable_setting_content_app),
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 30.dp)
        )
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.activity_main_composable_setting_content_kakaotalk_package_names),
                style = TextStyle(fontSize = 15.sp),
                color = Color.Black
            )
            OutlinedButton(onClick = { kakaoTalkPackageNamesDialogVisible.value = true }) {
                val kakaoTalkPackageNames =
                    app.kakaoTalkPackageNames.sortedBy { it == BotConstant.KakaoTalkDefaultPackageName }
                        .asReversed()
                val message =
                    if (kakaoTalkPackageNames.size == 1) kakaoTalkPackageNames.first()
                    else "${kakaoTalkPackageNames.first()} 외 ${kakaoTalkPackageNames.size - 1}개 추가됨"
                Text(text = message)
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.activity_main_composable_setting_content_noficiation_access_permission),
                style = TextStyle(fontSize = 15.sp),
                color = Color.Black
            )
            OutlinedButton(onClick = {
                NotificationUtil.requestNotificationListenerPermission(activity)
            }) {
                val isPermissionGranted =
                    NotificationUtil.isNotificationListenerPermissionGranted(context)
                val message =
                    if (isPermissionGranted) stringResource(R.string.activity_main_composable_setting_content_button_granted)
                    else stringResource(R.string.activity_main_composable_setting_content_button_denied)
                Text(text = message)
            }
        }
        if (Storage.isScoped) {
            RowContent(modifier = Modifier.padding(top = 8.dp)) {
                Text(
                    text = stringResource(R.string.activity_main_composable_setting_content_access_storage_manager_permission),
                    style = TextStyle(fontSize = 15.sp),
                    color = Color.Black
                )
                OutlinedButton(onClick = { Storage.requestStorageManagePermission(activity) }) {
                    val isPermissionGranted = Storage.isStorageManagerPermissionGranted()
                    val message =
                        if (isPermissionGranted) stringResource(R.string.activity_main_composable_setting_content_button_granted)
                        else stringResource(R.string.activity_main_composable_setting_content_button_denied)
                    Text(text = message)
                }
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.activity_main_composable_setting_content_battery_optimization),
                style = TextStyle(fontSize = 15.sp),
                color = Color.Black
            )
            OutlinedButton(onClick = { BatteryUtil.requestIgnoreBatteryOptimization(context) }) {
                val isOptimized = BatteryUtil.isIgnoringBatteryOptimization(context)
                val message = stringResource(
                    if (isOptimized) {
                        R.string.activity_main_composable_setting_content_button_granted
                    } else {
                        R.string.activity_main_composable_setting_content_button_denied
                    }
                )
                Text(text = message)
            }
        }
        Text(
            text = stringResource(R.string.activity_main_composable_setting_content_etc),
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 30.dp)
        )
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.activity_main_composable_setting_content_opensource_license),
                style = TextStyle(fontSize = 15.sp),
                color = Color.Black
            )
            OutlinedButton(onClick = {
                context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
            }) {
                Text(text = stringResource(R.string.activity_main_composable_setting_content_button_show))
            }
        }
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(R.string.activity_main_composable_setting_content_donate),
                style = TextStyle(fontSize = 15.sp),
                color = Color.Black
            )
            Button(
                modifier = Modifier.shimmer(duration = 1000),
                onClick = { donateDialogVisble.value = true }
            ) {
                Text(text = stringResource(R.string.activity_main_composable_setting_content_button_thanks))
            }
        }
        Text(
            text = stringResource(
                R.string.activity_main_composable_setting_content_donater,
                "클라이드님, 띠까님, 시계톡톡님" // TODO: firebase realtime update
            ),
            modifier = Modifier
                .horizontalScroll(rememberScrollState()) // TODO: Auto Scroll
                .padding(top = 15.dp),
            color = colors.primary
        )
        Spacer(modifier = Modifier.height(20.dp))
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
