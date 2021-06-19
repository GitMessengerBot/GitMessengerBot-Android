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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import me.sungbin.gitmessengerbot.theme.colors

private val vm = ScriptViewModel.instance

data class ScriptItem(
    val id: Int,
    val name: String,
    val type: Int, // ScriptType
    val power: Boolean,
    val compiled: Boolean,
    val lastRun: String
)

@Composable
fun LazyScript(modifier: Modifier) {
}

@Composable
fun Script(modifier: Modifier, scriptItem: ScriptItem) {
    val shape = RoundedCornerShape(30.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
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
    }
}
