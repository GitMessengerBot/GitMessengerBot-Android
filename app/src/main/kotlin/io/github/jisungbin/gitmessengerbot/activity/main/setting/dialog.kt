/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [dialog.kt] created by Ji Sungbin on 21. 7. 25. 오전 2:40.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.main.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.ui.licenser.License
import io.github.jisungbin.gitmessengerbot.ui.licenser.Licenser
import io.github.jisungbin.gitmessengerbot.ui.licenser.Project
import io.github.jisungbin.gitmessengerbot.util.Web

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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .padding(top = 16.dp, bottom = 16.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
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
            },
            title = {
                Text(text = stringResource(R.string.setting_dialog_donate))
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
        AlertDialog(
            onDismissRequest = { visible.value = false },
            buttons = {},
            text = {
            }
        )
    }
}

@Composable
fun ScriptAddDefaultLanguageDialog(visible: MutableState<Boolean>) {
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
