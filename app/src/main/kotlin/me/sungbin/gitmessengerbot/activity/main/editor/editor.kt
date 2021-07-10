/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [editor.kt] created by Ji Sungbin on 21. 7. 10. 오전 4:41.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.activity.main.script.ScriptItem
import me.sungbin.gitmessengerbot.activity.main.script.ts2js.Ts2JsRepo
import me.sungbin.gitmessengerbot.bot.Bot
import me.sungbin.gitmessengerbot.theme.colors
import me.sungbin.gitmessengerbot.util.extension.toast

@Composable
fun Editor(script: ScriptItem, ts2JsRepo: Ts2JsRepo) {
    var codeField by remember { mutableStateOf(TextFieldValue(Bot.getCode(script))) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ToolBar(
                script = script,
                codeField = codeField,
                ts2JsRepo = ts2JsRepo
            )
        }
    ) {
        TextField(
            value = codeField,
            onValueChange = { codeField = it },
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
private fun ToolBar(script: ScriptItem, codeField: TextFieldValue, ts2JsRepo: Ts2JsRepo) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(color = colors.primary)
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        val (menu, title, setting, save, reload, classList) = createRefs()

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
            painter = painterResource(R.drawable.ic_round_settings_24),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.constrainAs(setting) {
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
                    Bot.save(script, codeField.text)
                }
                .constrainAs(save) {
                    end.linkTo(setting.start, 10.dp)
                    top.linkTo(parent.top)
                }
        )
        Icon(
            painter = painterResource(R.drawable.ic_round_refresh_24),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .rotate(-90f)
                .clickable {
                    // todo: ts2js preview dialog
                    /*coroutineScope.launch {
                        ts2JsRepo
                            .convert(codeField.text)
                            .collect { result ->
                                println(
                                    when (result) {
                                        is Ts2JsResult.Success -> result.ts2js.tsCode
                                        is Ts2JsResult.Error -> result.exception
                                    }
                                )
                            }
                    }*/
                }
                .constrainAs(reload) {
                    end.linkTo(save.start, 10.dp)
                    top.linkTo(parent.top)
                }
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(classList) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(Bot.getClassList(script)) { scriptClass ->
                val shape = RoundedCornerShape(15.dp)
                Text(
                    text = scriptClass.name,
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(shape)
                        .background(color = Color.White, shape = shape)
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
                )
            }
        }
    }
}
