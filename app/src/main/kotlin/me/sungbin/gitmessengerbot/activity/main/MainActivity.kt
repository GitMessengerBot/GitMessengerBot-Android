/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [MainActivity.kt] created by Ji Sungbin on 21. 5. 31. 오후 11:12.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import me.sungbin.gitmessengerbot.activity.main.debug.DebugViewModel
import me.sungbin.gitmessengerbot.activity.main.script.ScriptViewModel
import me.sungbin.gitmessengerbot.activity.main.setting.SettingViewModel
import me.sungbin.gitmessengerbot.repo.github.model.GithubData
import me.sungbin.gitmessengerbot.theme.MaterialTheme
import me.sungbin.gitmessengerbot.theme.SystemUiController
import me.sungbin.gitmessengerbot.theme.colors
import me.sungbin.gitmessengerbot.theme.twiceLightGray
import me.sungbin.gitmessengerbot.ui.fancybottombar.FancyBottomBar
import me.sungbin.gitmessengerbot.ui.fancybottombar.FancyColors
import me.sungbin.gitmessengerbot.ui.fancybottombar.FancyItem
import me.sungbin.gitmessengerbot.ui.glideimage.GlideImage
import me.sungbin.gitmessengerbot.util.Storage
import me.sungbin.gitmessengerbot.util.config.PathConfig
import me.sungbin.gitmessengerbot.util.extension.toModel

class MainActivity : ComponentActivity() {

    private val scriptVm: ScriptViewModel by viewModels()
    private val settingVm: SettingViewModel by viewModels()
    private val debugVm: DebugViewModel by viewModels()

    private var tab by mutableStateOf(Tab.Script)
    private val githubData = Storage.read(PathConfig.GithubData, "")!!.toModel(GithubData::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).run {
            setStatusBarColor(colors.primary)
            setNavigationBarColor(Color.White)
        }

        setContent {
            MaterialTheme {
                Main()
            }
        }
    }

    @Composable
    private fun Main() {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (content, footer) = createRefs()

            Crossfade(targetState = tab) { index ->
                when (index) {
                    Tab.Script -> Content(
                        modifier = Modifier.constrainAs(content) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(footer.top)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        }
                    )
                    else -> Text(text = "TODO")
                }
            }
            Footer(
                modifier = Modifier.constrainAs(footer) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }
            )
        }
    }

    @Composable
    private fun Content(modifier: Modifier) {
        var botPower by remember { mutableStateOf(false) }

        Column(modifier = modifier) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
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
                    modifier = Modifier.constrainAs(appName) {
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 50f, topEnd = 50f))
                    .background(twiceLightGray)
            ) {
                // todo: LazyScript()
            }
        }
    }

    @Composable
    private fun Footer(modifier: Modifier) {
        val items = listOf(
            FancyItem(icon = R.drawable.ic_round_script_24, id = 0),
            FancyItem(icon = R.drawable.ic_round_debug_24, id = 1),
            FancyItem(icon = R.drawable.ic_round_github_24, id = 2),
            FancyItem(icon = R.drawable.ic_round_settings_24, id = 3)
        )

        Box(modifier = modifier) {
            FancyBottomBar(
                fancyColors = FancyColors(primary = colors.primary),
                items = items
            ) { tab = id }
            Surface(
                modifier = Modifier
                    .padding(bottom = 35.dp)
                    .size(50.dp),
                shape = CircleShape,
                color = colors.primary,
                elevation = 2.dp
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }

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
}
