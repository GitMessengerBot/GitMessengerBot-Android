/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [debug.kt] created by Ji Sungbin on 21. 6. 19. 오후 11:52.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.debug

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.bot.Bot
import me.sungbin.gitmessengerbot.theme.colors

@Composable
fun Debug() {
    Column(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { DebugToolbar() },
            content = { DebugContent() }
        )
    }
}

@Composable
private fun DebugToolbar() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = colors.primary)
            .padding(top = 10.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        val (title, setting, switchDescription, modeSwitch) = createRefs()
        var evalMode by remember { mutableStateOf(Bot.app.value.evalMode) }

        Text(
            text = "DebugRoom",
            color = Color.White,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
        Icon(
            painter = painterResource(R.drawable.ic_round_settings_24),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.constrainAs(setting) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
        Switch(
            checked = evalMode,
            onCheckedChange = {
                evalMode = !evalMode
                Bot.save(Bot.app.value.copy(evalMode = evalMode))
            },
            modifier = Modifier.constrainAs(modeSwitch) {
                end.linkTo(setting.start, 16.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = colors.primaryVariant,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.Black
            ),
        )
        Text(
            text = "eval mode",
            color = Color.White,
            fontSize = 13.sp,
            modifier = Modifier.constrainAs(switchDescription) {
                end.linkTo(modeSwitch.start, 5.dp)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        )
    }
}

@Composable
private fun DebugContent() {
    Text(
        text = "AA",
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
    )
}

@Composable
private fun ChatBubble() {

}
