/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Script.kt] created by Ji Sungbin on 21. 6. 19. 오후 10:56.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.script

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.repo.github.model.GithubData
import me.sungbin.gitmessengerbot.theme.colors
import me.sungbin.gitmessengerbot.theme.twiceLightGray
import me.sungbin.gitmessengerbot.ui.glideimage.GlideImage

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
fun ScriptContent(modifier: Modifier, githubData: GithubData, scriptVm: ScriptViewModel) {
    Column(modifier = modifier) {
        Header(githubData, scriptVm)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 50f, topEnd = 50f))
                .background(twiceLightGray)
                .padding(start = 30.dp, end = 30.dp, top = 20.dp, bottom = 20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
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
            LazyScript(scriptVm = scriptVm)
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
                            checkedThumbColor = Color.White,
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
private fun LazyScript(scriptVm: ScriptViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 70.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(items = List(100) { it }) { script ->
            Script(scriptItem = script)
        }
    }
}

@Composable
private fun Script(scriptItem: Int) {
    val shape = RoundedCornerShape(30.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(shape)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        colors.primary,
                        colors.secondary
                    )
                ),
                shape = shape
            )
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(text = scriptItem.toString())
    }
}
