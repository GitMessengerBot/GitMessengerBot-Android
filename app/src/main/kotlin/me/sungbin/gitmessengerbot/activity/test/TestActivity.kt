/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [TestActivity.kt] created by Ji Sungbin on 21. 7. 19. 오전 3:09.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random
import me.sungbin.gitmessengerbot.theme.MaterialTheme
import me.sungbin.gitmessengerbot.ui.timeline.TimeLine

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                TimeLineView()
            }
        }
    }

    @Composable
    private fun TimeLineView() {
        TimeLine(
            items = List(size = 10, init = { Random.nextInt() }),
            modifier = Modifier.background(Color.White)
        ) { modifier, item ->
            Surface(
                modifier = modifier,
                elevation = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(15.dp)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(30.dp)) {
                    Text(
                        text = item.toString(),
                        fontSize = 15.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}
