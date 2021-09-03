package io.github.jisungbin.gitmessengerbot.activity.editor.js

import androidx.compose.runtime.Composable
import io.github.jisungbin.gitmessengerbot.domain.github.model.commit.CommitContentItem
import io.github.jisungbin.gitmessengerbot.domain.github.model.commit.CommitListItem
import me.sungbin.timelineview.TimeLineItem

data class CommitHistoryItem(
    override val key: String,
    val list: CommitListItem,
    val content: CommitContentItem,
) : TimeLineItem<String>

@Composable
fun CommitList() {
}
