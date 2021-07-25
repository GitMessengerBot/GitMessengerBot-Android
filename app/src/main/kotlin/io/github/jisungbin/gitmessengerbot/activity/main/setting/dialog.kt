/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [dialog.kt] created by Ji Sungbin on 21. 7. 25. 오전 2:40.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.main.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import io.github.jisungbin.gitmessengerbot.activity.main.script.toScriptLangName
import io.github.jisungbin.gitmessengerbot.bot.Bot
import io.github.jisungbin.gitmessengerbot.theme.colors
import io.github.jisungbin.gitmessengerbot.ui.licenser.License
import io.github.jisungbin.gitmessengerbot.ui.licenser.Licenser
import io.github.jisungbin.gitmessengerbot.ui.licenser.Project
import io.github.jisungbin.gitmessengerbot.util.Util
import io.github.jisungbin.gitmessengerbot.util.Web
import io.github.jisungbin.gitmessengerbot.util.extension.noRippleClickable

@Composable
fun OpenSourceDialog(visible: MutableState<Boolean>) {
    if (visible.value) {
        AlertDialog(
            modifier = Modifier.width(300.dp),
            onDismissRequest = { visible.value = false },
            buttons = {},
            title = {
                Text(
                    text = stringResource(R.string.setting_dialog_opensource_license),
                    fontSize = 20.sp
                )
            },
            text = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .padding(top = 15.dp, bottom = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Licenser(
                        listOf(
                            Project(
                                "Kotlin",
                                "https://github.com/JetBrains/kotlin",
                                License.Apache2
                            ),
                            Project(
                                "Gradle",
                                "https://github.com/gradle/gradle",
                                License.Apache2
                            ),
                            Project(
                                "Android Icons",
                                "https://www.apache.org/licenses/LICENSE-2.0.txt",
                                License.Apache2
                            ),
                            Project(
                                "kotlinx.coroutines",
                                "https://github.com/Kotlin/kotlinx.coroutines",
                                License.Apache2
                            ),
                            Project(
                                "CoreKtx",
                                "https://android.googlesource.com/platform/frameworks/support/",
                                License.Apache2
                            ),
                            Project(
                                "Browser",
                                "https://developer.android.com/jetpack/androidx/releases/browser",
                                License.Apache2
                            ),
                            Project(
                                "CrashReporter",
                                "https://github.com/MindorksOpenSource/CrashReporter",
                                License.Apache2
                            ),
                            Project(
                                "okhttp",
                                "https://github.com/square/okhttp",
                                License.Apache2
                            ),
                            Project(
                                "retrofit",
                                "https://github.com/square/retrofit",
                                License.Apache2
                            ),
                            Project(
                                "Room",
                                "https://developer.android.com/jetpack/androidx/releases/room",
                                License.Apache2
                            ),
                            Project("Hilt", "https://dagger.dev/hilt/", License.Apache2),
                            Project(
                                "Jetpack Compose",
                                "https://developer.android.com/jetpack/compose",
                                License.Apache2
                            ),
                            Project(
                                "leakcanary",
                                "https://github.com/square/leakcanary",
                                License.Apache2
                            ),
                            Project(
                                "ConstraintLayout",
                                "https://developer.android.com/jetpack/compose/layouts/constraintlayout",
                                License.Apache2
                            ),
                            Project(
                                "chaquopy",
                                "https://github.com/chaquo/chaquopy",
                                License.MIT
                            ),
                            Project(
                                "FancyBottomBar",
                                "https://github.com/jisungbin/FancyBottomBar",
                                License.MIT
                            ),
                            Project(
                                "TimeLineView",
                                "https://github.com/jisungbin/TimeLineView",
                                License.MIT
                            ),
                            Project(
                                "gson",
                                "https://github.com/google/gson",
                                License.Apache2
                            ),
                            Project(
                                "TedKeyboardObserver",
                                "https://github.com/ParkSangGwon/TedKeyboardObserver",
                                License.Apache2
                            ),
                            Project(
                                "ViewColorGenerator",
                                "https://github.com/MindorksOpenSource/ViewColorGenerator",
                                License.Apache2
                            ),
                            Project(
                                "jsoup",
                                "https://github.com/jhy/jsoup",
                                License.MIT
                            ),
                            Project(
                                "J2V8",
                                "https://github.com/eclipsesource/J2V8",
                                License.Custom("EPL-1.0")
                            ),
                            Project(
                                "rhino",
                                "https://github.com/mozilla/rhino",
                                License.Custom("MPL-2.0")
                            )
                        )
                    )
                }
            }
        )
    }
}

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
                    Text(text = stringResource(R.string.setting_dialog_button_direct_go))
                }
                OutlinedButton(
                    onClick = {
                        Util.copy(context, context.getString(R.string.kakaotalk_id))
                    }
                ) {
                    Text(text = stringResource(R.string.setting_dialog_button_copy_kakaotalk_id))
                }
            },
            title = {
                Text(
                    text = stringResource(R.string.setting_dialog_donate),
                    modifier = Modifier.padding(bottom = 30.dp)
                )
            }
        )
    }
}

@Composable
fun GitDefaultCommitMessageDialog(visible: MutableState<Boolean>) {
    if (visible.value) {
        AlertDialog(
            onDismissRequest = { visible.value = false },
            buttons = {},
            text = {
            }
        )
    }
}

@Composable
fun GitDefaultCreateRepoOptionsDialog(visible: MutableState<Boolean>) {
    if (visible.value) {
        AlertDialog(
            onDismissRequest = { visible.value = false },
            buttons = {},
            text = {
            }
        )
    }
}

@Composable
fun KakaoTalkPackageNamesDialog(visible: MutableState<Boolean>) {
    if (visible.value) {
        var newKakaoTalkPackage by remember { mutableStateOf(TextFieldValue()) }
        val focusManager = LocalFocusManager.current

        AlertDialog(
            onDismissRequest = { visible.value = false },
            title = { Text(text = stringResource(R.string.setting_app_kakaotak_package_names)) },
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
                                        if (newKakaoTalkPackage.text.isNotBlank()) {
                                            Bot.app.value.kakaoTalkPackageNames.add(
                                                newKakaoTalkPackage.text
                                            )
                                            Bot.saveAndUpdate(Bot.app.value)
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
                        items = Bot.app.value.kakaoTalkPackageNames,
                        key = { it }
                    ) { packageName ->
                        ApplicationItem(packageName = packageName)
                    }
                }
            }
        )
    }
}

@Composable
fun ScriptAddDefaultLanguageDialog(visible: MutableState<Boolean>) {
    if (visible.value) {
        val scriptLangItemShape = RoundedCornerShape(10.dp)
        val scriptLangItemBackgroundColor =
            { lang: Int -> if (Bot.app.value.scriptDefaultLang.value == lang) colors.secondary else Color.White }
        val scriptLangItemTextColor =
            { lang: Int -> if (Bot.app.value.scriptDefaultLang.value == lang) Color.White else colors.secondary }

        AlertDialog(
            onDismissRequest = { visible.value = false },
            buttons = {},
            title = {
                Text(text = stringResource(R.string.setting_script_default_add_lang))
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Text(text = "", modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .clip(scriptLangItemShape)
                            .border(1.dp, colors.secondary, scriptLangItemShape)
                    ) {
                        repeat(4) { scriptLang ->
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .background(scriptLangItemBackgroundColor(scriptLang))
                                    .noRippleClickable {
                                        Bot.app.value.scriptDefaultLang.value = scriptLang
                                        Bot.saveAndUpdate(Bot.app.value)
                                    },
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
        AlertDialog(
            onDismissRequest = { visible.value = false },
            buttons = {},
            text = {
            }
        )
    }
}

@Composable
private fun ApplicationItem(packageName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = packageName, color = Color.Black, modifier = Modifier.padding(start = 15.dp))
        Icon(
            painter = painterResource(R.drawable.ic_round_trash_24),
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    Bot.app.value.kakaoTalkPackageNames.remove(packageName)
                    Bot.saveAndUpdate(Bot.app.value)
                }
        )
    }
}
