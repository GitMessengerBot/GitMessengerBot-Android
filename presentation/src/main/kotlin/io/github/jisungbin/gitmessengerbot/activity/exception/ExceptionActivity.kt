/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ExceptionActivity.kt] created by Ji Sungbin on 21. 9. 3. 오후 6:02
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.exception

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.firestore.DocumentReference
import dagger.hilt.android.AndroidEntryPoint
import io.github.jisungbin.erratum.ErratumExceptionActivity
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.common.constant.ExceptionConstant
import io.github.jisungbin.gitmessengerbot.theme.SystemUiController
import javax.inject.Inject

@AndroidEntryPoint
class ExceptionActivity : ErratumExceptionActivity() {

    @Inject
    lateinit var exceptionFirestoreDocument: DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exceptionFirestoreDocument.set(mapOf("data" to exceptionString!!))
        SystemUiController(window).setSystemBarsColor(Color.White)
        setContent {
            Content()
        }
    }

    @Composable
    private fun Content() {
        val content = ExceptionConstant.ObjectContents.random()
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.crying))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 30.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = Modifier.size(300.dp),
                iterations = LottieConstants.IterateForever,
                composition = composition,
            )
            Text(
                text = "깃메봇이가 예상치 못한 ${content}에 맞았어요 \uD83D\uDE22\n\n$exceptionString",
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Button(onClick = {
                finish()
                openLastActivity()
            }) {
                Text(
                    text = stringResource(
                        R.string.activity_exception_button_retry_with_avoid,
                        content
                    )
                )
            }
        }
    }
}
