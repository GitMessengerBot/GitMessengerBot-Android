package io.github.jisungbin.gitmessengerbot.activity.editor.js

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import io.github.jisungbin.gitmessengerbot.common.core.Web
import io.github.jisungbin.gitmessengerbot.domain.github.model.commit.CommitContentItem
import io.github.jisungbin.gitmessengerbot.domain.github.model.commit.CommitListItem
import io.github.jisungbin.gitmessengerbot.theme.colors
import io.github.jisungbin.gitmessengerbot.theme.twiceLightGray
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
        items = items,
        timeLineOption = TimeLineOption(
            circleColor = colors.primary,
            lineColor = colors.secondary,
            contentHeight = 100.dp
        ),
        header = { commitListItem ->
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(5.dp),
                backgroundColor = twiceLightGray,
                onClick = {
                    Web.open(context, Web.Link.Custom(commitListItem.htmlUrl))
                }
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                    val (message, date) = createRefs()

                    Text(
                        text = commitListItem.commit.message,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.constrainAs(message) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                    )
                    Text(
                        text = commitListItem.commit.committer.date,
                        modifier = Modifier.constrainAs(date) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                    )
                }
            }
        },
        content = { commitHistoryItem ->
            val commitContentItem = commitHistoryItem.items

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                    .clickable {
                        Web.open(context, Web.Link.Custom(commitContentItem.rawUrl))
                    }
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
    )
}
