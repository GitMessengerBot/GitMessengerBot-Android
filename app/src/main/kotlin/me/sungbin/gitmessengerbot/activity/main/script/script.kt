/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [script.kt] created by Ji Sungbin on 21. 6. 19. 오후 10:56.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.script

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import kotlin.random.Random
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.repo.github.model.GithubData
import me.sungbin.gitmessengerbot.theme.colors
import me.sungbin.gitmessengerbot.theme.twiceLightGray
import me.sungbin.gitmessengerbot.ui.glideimage.GlideImage
import me.sungbin.gitmessengerbot.util.Script
import me.sungbin.gitmessengerbot.util.extension.noRippleClickable

@Composable
private fun MenuBox(
    modifier: Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .width(75.dp)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .size(75.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            content()
        }
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

@Composable
fun ScriptContent(githubData: GithubData, scriptVm: ScriptViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        Header(githubData, scriptVm)
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
                    text = stringResource(R.string.script_label),
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            LazyScript(
                modifier = Modifier.padding(top = 15.dp),
                scriptVm = scriptVm
            )
        }
    }
}

@Composable
private fun Header(githubData: GithubData, scriptVm: ScriptViewModel) {
    var botPower by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        val (appName, profileName, profileImage, menuBoxes) = createRefs()

        GlideImage(
            src = githubData.profileImageUrl,
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .constrainAs(profileImage) {
                    end.linkTo(parent.end)
                    top.linkTo(appName.top)
                    bottom.linkTo(profileName.bottom)
                }
        )
        Text(
            text = stringResource(R.string.app_name),
            color = Color.LightGray,
            fontSize = 13.sp,
            modifier = Modifier.constrainAs(appName) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
        )
        Text(
            text = stringResource(
                R.string.main_welcome,
                githubData.userName
            ),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(profileName) {
                start.linkTo(parent.start)
                top.linkTo(appName.bottom, 8.dp)
            }
        )
        Row(
            modifier = Modifier.constrainAs(menuBoxes) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(profileName.bottom, 30.dp)
                width = Dimension.fillToConstraints
            },
            horizontalArrangement = Arrangement.Center
        ) {
            MenuBox(
                title = stringResource(R.string.main_power),
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(stringResource(R.string.main_menu_on))
                    Switch(
                        checked = botPower,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = colors.primaryVariant,
                            checkedTrackColor = colors.primary,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = colors.secondary
                        ),
                        onCheckedChange = { botPower = it }
                    )
                }
            }
            MenuBox(
                title = stringResource(R.string.main_menu_all_script_count),
                modifier = Modifier.weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = scriptVm.scripts.size.toString(), fontSize = 35.sp)
                    Text(text = "개", fontSize = 8.sp)
                }
            }
            MenuBox(
                title = stringResource(R.string.main_menu_running_script_count),
                modifier = Modifier.weight(1f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = scriptVm.scripts.filter { it.power }.size.toString(),
                        fontSize = 35.sp
                    )
                    Text(text = "개", fontSize = 8.sp)
                }
            }
            MenuBox(
                title = stringResource(R.string.main_menu_script_search),
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_round_search_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp),
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
private fun LazyScript(modifier: Modifier, scriptVm: ScriptViewModel) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White, RoundedCornerShape(20.dp)),
        contentPadding = PaddingValues(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(
            items = List(20) {
                ScriptItem(
                    id = Random.nextInt(),
                    name = "테스트 $it",
                    lang = Random.nextInt(0, 4),
                    power = Random.nextBoolean(),
                    compiled = Random.nextBoolean(),
                    lastRun = "마지막 작동 시간"
                )
            }
        ) { script ->
            ScriptView(script = script)
        }
    }
}

@Composable
private fun ScriptView(script: ScriptItem) {
    val shape = RoundedCornerShape(20.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .shadow(3.dp, shape)
            .clip(shape)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF7d27bb),
                        Color(0xFFb66aed)
                    )
                ),
                shape = shape
            )
            .padding(top = 10.dp, bottom = 10.dp, start = 15.dp, end = 15.dp)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (
                compileState, reload, edit, debug, logcat, setting, delete,
                scriptName, scriptNameUnderline,
                scriptLangDeco, scriptLang, scriptLastRunTime, scriptPower
            ) = createRefs()

            val compileStateShape = RoundedCornerShape(15.dp)
            val compileStateBackgroundColor by animateColorAsState(if (script.compiled) Color.White else Color.Transparent)
            val compileStateTextColor by animateColorAsState(if (script.compiled) colors.primary else Color.White)

            Text(
                text = stringResource(R.string.script_item_label_compile),
                color = compileStateTextColor,
                fontSize = 13.sp,
                modifier = Modifier
                    .clip(compileStateShape)
                    .background(compileStateBackgroundColor, compileStateShape)
                    .border(1.dp, Color.White, compileStateShape)
                    .padding(top = 2.dp, bottom = 2.dp, start = 4.dp, end = 4.dp)
                    .constrainAs(compileState) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    },
            )
            Icon(
                painter = painterResource(R.drawable.ic_round_cancel_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .constrainAs(delete) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
            )
            Icon(
                painter = painterResource(R.drawable.ic_round_settings_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .constrainAs(setting) {
                        end.linkTo(delete.start, 8.dp)
                        top.linkTo(parent.top)
                    }
            )
            Icon(
                painter = painterResource(R.drawable.ic_round_logcat_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .constrainAs(logcat) {
                        end.linkTo(setting.start, 8.dp)
                        top.linkTo(parent.top)
                    }
            )
            Icon(
                painter = painterResource(R.drawable.ic_round_debug_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .constrainAs(debug) {
                        end.linkTo(logcat.start, 8.dp)
                        top.linkTo(parent.top)
                    }
            )
            Icon(
                painter = painterResource(R.drawable.ic_round_pen_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(20.dp)
                    .constrainAs(edit) {
                        end.linkTo(debug.start, 8.dp)
                        top.linkTo(parent.top)
                    }
            )
            Icon(
                painter = painterResource(R.drawable.ic_round_refresh_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(25.dp)
                    .rotate(-90f)
                    .constrainAs(reload) {
                        end.linkTo(edit.start, 8.dp)
                        top.linkTo(parent.top)
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
                        bottom.linkTo(parent.bottom)
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
                fontSize = 13.sp
            )
            Switch(
                checked = script.power,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = colors.primaryVariant,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.Black
                ),
                onCheckedChange = {},
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
                fontSize = 13.sp
            )
            Divider(
                modifier = Modifier.constrainAs(scriptNameUnderline) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(scriptPower.top, 4.dp)
                    width = Dimension.fillToConstraints
                },
                color = Color.White,
                thickness = 0.5.dp
            )
            Text(
                text = script.name,
                color = Color.White,
                modifier = Modifier.constrainAs(scriptName) {
                    bottom.linkTo(scriptNameUnderline.bottom, 4.dp)
                    start.linkTo(parent.start)
                }
            )
        }
    }
}

@Composable
fun ScriptAddContent() {
    var scriptNameField by remember { mutableStateOf(TextFieldValue()) }
    var selectedScriptLang by remember { mutableStateOf(ScriptType.TypeScript) }
    val scriptLangSpinnerShape = RoundedCornerShape(10.dp)
    val focusManager = LocalFocusManager.current
    val scriptLangSpinnerBackgroundColor =
        { lang: Int -> if (selectedScriptLang == lang) colors.secondary else Color.White }
    val scriptLangSpinnerTextColor =
        { lang: Int -> if (selectedScriptLang == lang) Color.White else colors.secondary }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 30.dp, end = 30.dp, bottom = 30.dp, top = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.main_scriptadd_script_name))
            TextField(
                value = scriptNameField,
                onValueChange = { scriptNameField = it },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
                modifier = Modifier
                    .width(200.dp)
                    .focusRequester(FocusRequester()),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
            )
        }
        Text(
            text = stringResource(R.string.main_scriptadd_script_lang),
            modifier = Modifier.padding(top = 15.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(top = 5.dp)
                .clip(scriptLangSpinnerShape)
                .border(1.dp, colors.secondary, scriptLangSpinnerShape)
        ) {
            repeat(4) { scriptLang ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(scriptLangSpinnerBackgroundColor(scriptLang))
                        .noRippleClickable { selectedScriptLang = scriptLang },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = scriptLang.toScriptLangName(),
                        color = scriptLangSpinnerTextColor(scriptLang),
                        fontSize = 10.sp
                    )
                }
            }
        }
        Button(
            onClick = {
                val scriptName = scriptNameField.text
                val scriptItem = ScriptItem(
                    id = Random.nextInt(),
                    name = scriptName,
                    power = false,
                    lang = selectedScriptLang,
                    compiled = false,
                    lastRun = ""
                )
                Script.create(scriptItem)
                scriptNameField = TextFieldValue()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colors.secondary)
        ) {
            Text(
                text = stringResource(R.string.main_scriptadd_add_script),
                color = Color.White
            )
        }
    }
}
