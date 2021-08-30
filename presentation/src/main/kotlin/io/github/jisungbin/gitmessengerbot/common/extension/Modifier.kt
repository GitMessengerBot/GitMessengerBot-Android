/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Modifier.kt] created by Ji Sungbin on 21. 8. 28. 오후 2:35.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.common.extension

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.InspectorValueInfo
import androidx.compose.ui.platform.NoInspectorInfo

// https://stackoverflow.com/a/66839858/14299073
@OptIn(ExperimentalFoundationApi::class)
inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit,
    crossinline onLongClick: (() -> Unit) = {},
) = then(
    ComposedModifier {
        combinedClickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            onClick = { onClick() },
            onLongClick = { onLongClick() }
        )
    }
)

@PublishedApi
internal class ComposedModifier(
    val factory: @Composable Modifier.() -> Modifier,
) : Modifier.Element, InspectorValueInfo(NoInspectorInfo)
