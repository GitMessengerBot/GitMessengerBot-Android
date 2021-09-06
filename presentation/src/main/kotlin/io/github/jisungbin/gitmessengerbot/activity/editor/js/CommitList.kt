/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [CommitList.kt] created by Ji Sungbin on 21. 9. 5. 오후 11:41
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.editor.js

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import io.github.jisungbin.gitmessengerbot.common.core.Web
import io.github.jisungbin.gitmessengerbot.domain.github.model.commit.CommitContentItem
import io.github.jisungbin.gitmessengerbot.domain.github.model.commit.CommitListItem
import io.github.jisungbin.gitmessengerbot.theme.colors
import io.github.jisungbin.gitmessengerbot.theme.twiceLightGray
import io.github.jisungbin.gitmessengerbot.util.ISO8601Util
import io.github.jisungbin.gitmessengerbot.util.extension.noRippleClickable
import me.sungbin.timelineview.TimeLine
import me.sungbin.timelineview.TimeLineItem
import me.sungbin.timelineview.TimeLineOption

data class CommitHistoryItem(
    override val key: CommitListItem,
    val items: CommitContentItem,
) : TimeLineItem<CommitListItem>

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommitList(modifier: Modifier, items: List<CommitHistoryItem>) {
    val context = LocalContext.current

    TimeLine(
        modifier = modifier,
        items = items.sortedByDescending { it.key.date },
        timeLineOption = TimeLineOption(
            circleColor = colors.primary,
            lineColor = colors.secondary,
            contentHeight = 100.dp
        ),
        header = { commitListItem ->
            key(commitListItem.sha) {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(5.dp),
                    backgroundColor = twiceLightGray,
                    onClick = {
                        Web.open(context, Web.Link.Custom(commitListItem.htmlUrl))
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = commitListItem.message,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 15.sp,
                            maxLines = 2
                        )
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            text = ISO8601Util.convertKST(commitListItem.date),
                            fontSize = 10.sp
                        )
                    }
                }
            }
        },
        content = { commitHistoryItem ->
            key(commitHistoryItem.items.rawUrl) {
                val commitContentItem = commitHistoryItem.items

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                        .noRippleClickable(onClick = {
                            Web.open(context, Web.Link.Custom(commitContentItem.rawUrl))
                        })
                ) {
                    val (fileName, additions, changes, deletions) = createRefs()

                    Text(
                        text = commitContentItem.filename,
                        modifier = Modifier.constrainAs(fileName) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                    )
                    Text(
                        text = "+ ${commitContentItem.additions}",
                        modifier = Modifier.constrainAs(additions) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                    )
                    Text(
                        text = "* ${commitContentItem.changes}",
                        modifier = Modifier.constrainAs(changes) {
                            top.linkTo(additions.bottom)
                            bottom.linkTo(deletions.top)
                            end.linkTo(parent.end)
                        }
                    )
                    Text(
                        text = "- ${commitContentItem.deletions}",
                        modifier = Modifier.constrainAs(deletions) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                    )
                }
            }
        }
    )
}
