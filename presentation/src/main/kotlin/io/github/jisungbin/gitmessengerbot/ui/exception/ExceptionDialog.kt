/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ExceptionDialog.kt] created by Ji Sungbin on 21. 9. 3. 오후 4:49
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.ui.exception

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.jisungbin.gitmessengerbot.common.core.Util

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExceptionDialog(exception: MutableState<Exception?>) {
    if (exception.value != null) {
        val context = LocalContext.current
        val contents = listOf("눈덩이", "돌덩이", "나뭇가지", "새똥", "나뭇잎", "흙더미")
        val exceptionMessage = exception.value!!.message ?: "오류를 불러올 수 없음"

        AlertDialog(
            onDismissRequest = { exception.value = null },
            buttons = {},
            shape = RoundedCornerShape(30.dp),
            text = {
                Text(
                    text = "깃메봇이가 예상치 못한 ${contents.random()}에 맞았어요 \uD83E\uDD72\n\n$exceptionMessage",
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .combinedClickable(
                            onClick = {},
                            onLongClick = {
                                Util.copy(context, exceptionMessage)
                            }
                        )
                )
            }
        )
    }
}
