package io.github.jisungbin.gitmessengerbot.ui.timelineview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class TimeLinePadding(
    val defaultPadding: PaddingValues = PaddingValues(16.dp),
    val contentStart: Dp = 4.dp,
    val circleLineGap: Dp = 1.dp
)
