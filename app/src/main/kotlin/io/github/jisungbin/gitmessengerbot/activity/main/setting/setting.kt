/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [setting.kt] created by Ji Sungbin on 21. 6. 19. 오후 11:54.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.main.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.jisungbin.gitmessengerbot.bot.Bot
import io.github.jisungbin.gitmessengerbot.theme.transparentTextFieldColors

@Composable
fun Setting() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        var editorFontSize by remember { mutableStateOf(Bot.app.value.editorFontSize) }
        var editorAutoSave by remember { mutableStateOf(Bot.app.value.editorAutoSave) }
        var scriptDefaultCode by remember { mutableStateOf(Bot.app.value.scriptDefaultCode) }
        var scriptResponseFunctionName by remember { mutableStateOf(Bot.app.value.scriptResponseFunctioName) }
        var scriptDefaultAddLang by remember { mutableStateOf(Bot.app.value.scriptDefaultLang) }
        var gitDefaultBranch by remember { mutableStateOf(Bot.app.value.gitDefaultBranch) }
        var gitDefaultCommitMessage by remember { mutableStateOf(Bot.app.value.gitDefaultCommitMessage) }
        var gitDefaultRepoOptions by remember { mutableStateOf(Bot.app.value.gitDefaultRepoOptions) }
        var kakaoTalkPackageNames = remember {
            mutableStateListOf<String>().apply {
                addAll(Bot.app.value.kakaoTalkPackageNames)
            }
        }

        Text(text = "에디터", fontSize = 13.sp, color = Color.LightGray)
        RowContent(modifier = Modifier.padding(top = 8.dp)) {
            Text(text = "폰트 사이즈", fontSize = 15.sp, color = Color.Black)
            TextField(
                value = editorFontSize.toString(),
                onValueChange = {
                    try {
                        editorFontSize = it.toInt()
                    } catch (ignored: Exception) {
                    }
                },
                colors = transparentTextFieldColors(),
                modifier = Modifier
                    .wrapContentHeight()
                    .width(50.dp)
            )
        }
        RowContent(modifier = Modifier.padding(top = 4.dp)) {
            Text(text = "자동 저장 (분 단위, 0: 비활성화)", fontSize = 15.sp, color = Color.Black)
            TextField(
                value = editorFontSize.toString(),
                onValueChange = {
                    try {
                        editorFontSize = it.toInt()
                    } catch (ignored: Exception) {
                    }
                },
                colors = transparentTextFieldColors(),
                modifier = Modifier
                    .wrapContentHeight()
                    .width(50.dp)
            )
        }
    }
}

@Composable
private fun RowContent(modifier: Modifier, content: @Composable () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        content()
    }
}
