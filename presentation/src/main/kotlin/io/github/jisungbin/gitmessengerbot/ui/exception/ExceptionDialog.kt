/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ExceptionDialog.kt] created by Ji Sungbin on 21. 9. 3. 오후 4:49
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.ui.exception

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.common.core.Util

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ExceptionDialog(visible: MutableState<Boolean>, exception: Exception) {
    if (visible.value) {
        val context = LocalContext.current
        val contents = listOf("눈덩이", "돌덩이", "나뭇가지", "새똥", "나뭇잎", "흙더미")
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.something_error_2))

        AlertDialog(
            onDismissRequest = { visible.value = false },
            properties = DialogProperties(usePlatformDefaultWidth = false),
            confirmButton = {
                exception.message?.let { exceptionMessage ->
                    OutlinedButton(onClick = { Util.copy(context, exceptionMessage) }) {
                        Text(text = "에러 복사")
                    }
                }
            },
            shape = RoundedCornerShape(30.dp),
            title = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.wrapContentSize()
                ) {
                    LottieAnimation(
                        iterations = LottieConstants.IterateForever,
                        composition = composition,
                        modifier = Modifier.size(200.dp)
                    )
                }
            },
            text = {
                Text(
                    text = "깃메봇이가 예상치 못한 ${contents.random()}에 맞았어요 \uD83E\uDD72\n${exception.message}",
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }
}
