/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [debug.kt] created by Ji Sungbin on 21. 6. 19. 오후 11:52.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.debug

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.launch
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.bot.Bot
import me.sungbin.gitmessengerbot.bot.debug.DebugItem
import me.sungbin.gitmessengerbot.bot.debug.DebugStore
import me.sungbin.gitmessengerbot.bot.debug.Sender
import me.sungbin.gitmessengerbot.bot.debug.createDebugItem
import me.sungbin.gitmessengerbot.theme.colors
import me.sungbin.gitmessengerbot.theme.twiceLightGray
import me.sungbin.gitmessengerbot.util.Util
import me.sungbin.gitmessengerbot.util.config.StringConfig

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
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(twiceLightGray)
    ) {
        val (chats, textfield) = createRefs()
        val lazyListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        var inputField by remember { mutableStateOf(TextFieldValue()) }

        coroutineScope.launch {
            lazyListState.animateScrollToItem(DebugStore.items.size)
        }

        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .constrainAs(chats) {
                    top.linkTo(parent.top)
                    bottom.linkTo(textfield.top, 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                },
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val items = DebugStore.items

            itemsIndexed(items) { index, _ ->
                ChatBubble(
                    preItem = items.getOrNull(index - 1),
                    item = items[index],
                    nextItem = items.getOrNull(index + 1)
                )
            }
        }
        TextField(
            value = inputField,
            onValueChange = { inputField = it },
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.ic_round_send_24),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        DebugStore.add(
                            createDebugItem(
                                StringConfig.DebugAllBot,
                                inputField.text,
                                "null", // todo: Profile image
                                "Sender" // todo: Sender name
                            )
                        )
                        inputField = TextFieldValue()
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(DebugStore.items.size)
                        }
                    }
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.White
            ),
            modifier = Modifier
                .padding(16.dp)
                .requiredHeightIn(min = Dp.Unspecified, max = 150.dp)
                .constrainAs(textfield) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                },
            shape = RoundedCornerShape(30.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ChatBubble(preItem: DebugItem?, item: DebugItem, nextItem: DebugItem?) {
    val context = LocalContext.current
    val date = SimpleDateFormat("a hh:mm", Locale.KOREA).format(Date().apply { time = item.time })
    val nextDate = SimpleDateFormat("a hh:mm", Locale.KOREA).format(
        Date().apply { time = nextItem?.time ?: item.time }
    )
    val visibleTime = if (nextItem != null) {
        date != nextDate
    } else {
        true
    }
    val visibleProfileImage = if (preItem != null) {
        item.sender != preItem.sender
    } else {
        true
    }

    when (item.sender) {
        Sender.Bot -> { // 왼쪽 (봇이 답장)
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val (message, time) = createRefs()

                Surface(
                    modifier = Modifier
                        .combinedClickable(
                            onClick = {},
                            onLongClick = {
                                Util.copy(context, item.message)
                            }
                        )
                        .constrainAs(message) {
                            start.linkTo(parent.start)
                        },
                    elevation = 1.dp,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = item.message,
                        color = Color.Black,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .requiredWidthIn(min = Dp.Unspecified, max = 200.dp)
                            .padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp)
                    )
                }
                if (visibleTime) {
                    Text(
                        text = date,
                        color = Color.Gray,
                        fontSize = 10.sp,
                        modifier = Modifier.constrainAs(time) {
                            end.linkTo(message.end)
                            top.linkTo(message.bottom, 5.dp)
                        }
                    )
                }
            }
        }
        else -> { // 오른쪽 (사용자가 입력)
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val (profileImage, name, message, time) = createRefs()

                if (visibleProfileImage) {
                    Icon(
                        painter = painterResource(R.drawable.ic_round_account_circle_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .constrainAs(profileImage) {
                                end.linkTo(parent.end)
                            }
                    )
                    Text(
                        text = item.sender,
                        fontSize = 13.sp,
                        modifier = Modifier.constrainAs(name) {
                            end.linkTo(parent.end, 60.dp)
                        }
                    )
                    Surface(
                        modifier = Modifier
                            .combinedClickable(
                                onClick = {},
                                onLongClick = {
                                    Util.copy(context, item.message)
                                }
                            )
                            .constrainAs(message) {
                                top.linkTo(name.bottom, 10.dp)
                                end.linkTo(name.end)
                            },
                        elevation = 1.dp,
                        contentColor = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = item.message,
                            color = Color.Black,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .requiredWidthIn(min = Dp.Unspecified, max = 200.dp)
                                .padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp)
                        )
                    }
                } else {
                    Surface(
                        modifier = Modifier
                            .combinedClickable(
                                onClick = {},
                                onLongClick = {
                                    Util.copy(context, item.message)
                                }
                            )
                            .constrainAs(message) {
                                end.linkTo(parent.end, 60.dp)
                            },
                        elevation = 1.dp,
                        contentColor = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = item.message,
                            color = Color.Black,
                            fontSize = 15.sp,
                            modifier = Modifier
                                .requiredWidthIn(min = Dp.Unspecified, max = 200.dp)
                                .padding(top = 4.dp, bottom = 4.dp, start = 8.dp, end = 8.dp)
                        )
                    }
                }
                if (visibleTime) {
                    Text(
                        text = date,
                        color = Color.Gray,
                        fontSize = 10.sp,
                        modifier = Modifier.constrainAs(time) {
                            start.linkTo(message.start)
                            top.linkTo(message.bottom, 5.dp)
                        }
                    )
                }
            }
        }
    }
}
