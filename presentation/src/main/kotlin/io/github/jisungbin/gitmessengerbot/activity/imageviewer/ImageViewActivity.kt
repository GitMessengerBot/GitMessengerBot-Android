/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ImageViewActivity.kt] created by Ji Sungbin on 21. 7. 18. 오전 12:08.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.imageviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.palette.BitmapPalette
import io.github.jisungbin.gitmessengerbot.common.constant.IntentConstant
import io.github.jisungbin.gitmessengerbot.theme.MaterialTheme
import io.github.jisungbin.gitmessengerbot.theme.SystemUiController

class ImageViewActivity : ComponentActivity() {

    private var onBackPressed by mutableStateOf(false)
    private var dominantColor by mutableStateOf(Color.White)

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imageUrl = intent.getStringExtra(IntentConstant.ImageUrl)!!

        setContent {
            MaterialTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(dominantColor),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedVisibility(
                        visible = !onBackPressed,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        CoilImage(
                            imageModel = imageUrl,
                            modifier = Modifier.wrapContentSize(),
                            circularReveal = CircularReveal(),
                            bitmapPalette = BitmapPalette { palette ->
                                dominantColor = Color(palette.dominantSwatch?.rgb ?: 0)
                                SystemUiController(window).setSystemBarsColor(dominantColor)
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        onBackPressed = true
        finishAfterTransition()
    }
}
