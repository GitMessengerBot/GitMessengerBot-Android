/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Modifier.kt] created by Ji Sungbin on 21. 8. 28. 오후 2:35.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.util.extension

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

// https://stackoverflow.com/a/66839858/14299073s
@OptIn(ExperimentalFoundationApi::class)
inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit,
    crossinline onLongClick: (() -> Unit) = {},
) = composed {
    combinedClickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = { onClick() },
        onLongClick = { onLongClick() }
    )
}

fun Modifier.shimmer(duration: Int) = composed {
    val shimmer = rememberShimmer(
        shimmerBounds = ShimmerBounds.View,
        theme = createCustomTheme(duration),
    )
    shimmer(customShimmer = shimmer)
}

private fun createCustomTheme(duration: Int) = defaultShimmerTheme.copy(
    animationSpec = infiniteRepeatable(
        animation = tween(
            durationMillis = duration,
            delayMillis = 2_400,
            easing = LinearEasing,
        ),
        repeatMode = RepeatMode.Restart,
    ),
    rotation = 5f,
    shaderColors = listOf(
        Color.Unspecified.copy(alpha = 1.0f),
        Color.Unspecified.copy(alpha = 0.2f),
        Color.Unspecified.copy(alpha = 1.0f),
    ),
    shaderColorStops = null,
    shimmerWidth = 200.dp,
)
