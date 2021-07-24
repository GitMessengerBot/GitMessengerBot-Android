/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [header.kt] created by Ji Sungbin on 21. 7. 24. 오전 1:28.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.main.script

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.skydoves.landscapist.coil.CoilImage
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.activity.setup.github.model.GithubData
import io.github.jisungbin.gitmessengerbot.bot.Bot
import io.github.jisungbin.gitmessengerbot.service.BackgroundService
import io.github.jisungbin.gitmessengerbot.theme.colors
import io.github.jisungbin.gitmessengerbot.theme.transparentTextFieldColors
import io.github.jisungbin.gitmessengerbot.ui.imageviewer.ImageViewActivity
import io.github.jisungbin.gitmessengerbot.util.Json
import io.github.jisungbin.gitmessengerbot.util.Storage
import io.github.jisungbin.gitmessengerbot.util.config.StringConfig
import io.github.jisungbin.gitmessengerbot.util.extension.noRippleClickable

@Composable
private fun MenuBox(
    modifier: Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .width(65.dp)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .size(55.dp)
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
fun Header(activity: Activity, searchField: MutableState<TextFieldValue>) {
    val context = LocalContext.current
    val githubJson = Storage.read(StringConfig.GithubData, "")!!
    val githubData = Json.toModel(githubJson, GithubData::class)
    var power by remember { mutableStateOf(Bot.app.value.power) }
    var searching by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        val (appName, profileName, profileImage, menuBoxes) = createRefs()

        CoilImage(
            imageModel = githubData.profileImageUrl,
            modifier = Modifier
                .size(65.dp)
                .clip(CircleShape)
                .noRippleClickable {
                    val intent = Intent(activity, ImageViewActivity::class.java).apply {
                        putExtra(StringConfig.IntentImageUrl, githubData.profileImageUrl)
                    }
                    activity.startActivity(
                        intent,
                        ActivityOptions
                            .makeSceneTransitionAnimation(activity)
                            .toBundle()
                    )
                }
                .constrainAs(profileImage) {
                    end.linkTo(parent.end)
                    top.linkTo(appName.top)
                    bottom.linkTo(profileName.bottom)
                },
            contentScale = ContentScale.Crop,
            circularRevealedEnabled = true
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
        Crossfade(
            targetState = searching,
            modifier = Modifier.constrainAs(menuBoxes) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(profileName.bottom, 30.dp)
                width = Dimension.fillToConstraints
                height = Dimension.value(90.dp)
            }
        ) { state ->
            if (!state) {
                Row(
                    modifier = Modifier.fillMaxSize(),
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
                            Text(
                                if (power) stringResource(R.string.main_menu_on)
                                else stringResource(R.string.main_menu_off)
                            )
                            Switch(
                                checked = power,
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = colors.primaryVariant,
                                    uncheckedThumbColor = Color.White,
                                    uncheckedTrackColor = colors.secondary
                                ),
                                onCheckedChange = {
                                    if (it) {
                                        context.startService(
                                            Intent(
                                                context,
                                                BackgroundService::class.java
                                            )
                                        )
                                    } else {
                                        context.stopService(
                                            Intent(
                                                context,
                                                BackgroundService::class.java
                                            )
                                        )
                                    }
                                    power = it
                                    Bot.save(Bot.app.value.copy(power = power))
                                }
                            )
                        }
                    }
                    MenuBox(
                        title = stringResource(R.string.main_menu_all_script_count),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = Bot.scripts.size.toString(), fontSize = 25.sp)
                            Text(text = "개", fontSize = 8.sp)
                        }
                    }
                    MenuBox(
                        title = stringResource(R.string.main_menu_running_script_count),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = Bot.getPowerOnScripts().size.toString(),
                                fontSize = 25.sp
                            )
                            Text(text = "개", fontSize = 8.sp)
                        }
                    }
                    MenuBox(
                        title = stringResource(R.string.main_menu_script_search),
                        modifier = Modifier
                            .weight(1f)
                            .noRippleClickable {
                                searching = true
                            }
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
            } else { // 검색중
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = searchField.value,
                        onValueChange = { searchField.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .focusRequester(FocusRequester()),
                        shape = RoundedCornerShape(30.dp),
                        colors = transparentTextFieldColors(
                            backgroundColor = colors.secondary,
                            textColor = Color.White
                        ),
                        leadingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_round_search_24),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        trailingIcon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_round_cancel_in_circle_24),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.noRippleClickable {
                                    focusManager.clearFocus()
                                    searching = false
                                    searchField.value = TextFieldValue()
                                }
                            )
                        },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.main_header_script_search),
                                color = Color.LightGray
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() })
                    )
                }
            }
        }
    }
}
