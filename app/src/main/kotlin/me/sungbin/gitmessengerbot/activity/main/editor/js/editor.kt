/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [editor.kt] created by Ji Sungbin on 21. 7. 10. 오전 4:41.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.editor.js

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.activity.main.editor.beautify.repo.BeautifyRepo
import me.sungbin.gitmessengerbot.activity.main.editor.git.repo.GitRepo
import me.sungbin.gitmessengerbot.activity.main.script.ScriptItem
import me.sungbin.gitmessengerbot.bot.Bot
import me.sungbin.gitmessengerbot.theme.colors
import me.sungbin.gitmessengerbot.util.extension.toast

@Composable
fun Editor(script: ScriptItem) {
    val codeField = remember { mutableStateOf(TextFieldValue(Bot.getCode(script))) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ToolBar(
                script = script,
                codeField = codeField
            )
        }
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

@Composable
private fun GitMenu(
    gitRepo: GitRepo,
    beautifyRepo: BeautifyRepo,
    visible: MutableState<Boolean>,
    code: MutableState<TextFieldValue>
) {
    val coroutineState = rememberCoroutineScope()

    DropdownMenu(
        expanded = visible.value,
        onDismissRequest = { visible.value = false }
    ) {
        DropdownMenuItem(onClick = {

        }) {
            Text(text = "Commit and Push")
        }
        DropdownMenuItem(onClick = {

        }) {
            Text(text = "Update project")
        }
        Divider()
        DropdownMenuItem(onClick = {

        }) {
            Text(text = "Minify")
        }
        DropdownMenuItem(onClick = {

        }) {
            Text(text = "Beautify")
        }
    }
}

@Composable
private fun ToolBar(
    script: ScriptItem,
    codeField: MutableState<TextFieldValue>
) {
    val context = LocalContext.current
    val gitMenuVisible = remember { mutableStateOf(false) }

    GitMenu(visible = gitMenuVisible, code = codeField)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = colors.primary)
            .padding(top = 10.dp, bottom = 16.dp)
    ) {
        val (menu, title, setting, save, reload) = createRefs()

        Icon(
            painter = painterResource(R.drawable.ic_round_menu_24),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.constrainAs(menu) {
                start.linkTo(parent.start, 16.dp)
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
            painter = painterResource(R.drawable.ic_round_code_24),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .clickable { gitMenuVisible.value = true }
                .constrainAs(setting) {
                    end.linkTo(parent.end, 16.dp)
                    top.linkTo(parent.top)
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
                    end.linkTo(setting.start, 16.dp)
                    top.linkTo(parent.top)
                }
        )
    }
}
