/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [TimeLine.kt] created by Ji Sungbin on 21. 7. 19. 오전 2:25.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.ui.timeline

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun <T> TimeLine(
    items: List<T>,
    modifier: Modifier,
    timeLineOption: TimeLineOption = TimeLineOption(),
    timeLinePadding: TimeLinePadding = TimeLinePadding(),
    content: @Composable (Modifier, T) -> Unit
) {
    LazyColumn(modifier = modifier, contentPadding = timeLinePadding.defaultPadding) {
        itemsIndexed(items) { index, item ->
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(timeLineOption.contentHeight)
            ) {
                val (circle, topLine, bottomLine, timeLineContent) = createRefs()

                Icon(
                    painter = painterResource(timeLineOption.circleIcon),
                    contentDescription = null,
                    tint = timeLineOption.circleColor,
                    modifier = Modifier.constrainAs(circle) {
                        start.linkTo(parent.start)
                        top.linkTo(timeLineContent.top)
                        bottom.linkTo(timeLineContent.bottom)
                    }
                )
                content(
                    Modifier.constrainAs(timeLineContent) {
                        start.linkTo(circle.end, timeLinePadding.contentStart)
                        top.linkTo(
                            parent.top,
                            if (index != 0) timeLinePadding.contentTop else 0.dp
                        )
                        bottom.linkTo(
                            parent.bottom,
                            if (index != items.size - 1) timeLinePadding.contentBottom else 0.dp
                        )
                    },
                    item
                )
                if (index != 0) {
                    Divider(
                        modifier = Modifier.constrainAs(topLine) {
                            top.linkTo(parent.top)
                            bottom.linkTo(circle.top, timeLinePadding.circleLineGap)
                            start.linkTo(circle.start)
                            end.linkTo(circle.end)
                            width = Dimension.value(timeLineOption.lineWidth)
                            height = Dimension.fillToConstraints
                        },
                        color = timeLineOption.lineColor
                    )
                }
                if (index != items.size - 1) {
                    Divider(
                        modifier = Modifier.constrainAs(bottomLine) {
                            top.linkTo(circle.bottom, timeLinePadding.circleLineGap)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(circle.start)
                            end.linkTo(circle.end)
                            width = Dimension.value(timeLineOption.lineWidth)
                            height = Dimension.fillToConstraints
                        },
                        color = timeLineOption.lineColor
                    )
                }
            }
        }
    }
}
