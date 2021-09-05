/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [CommitContentItem.kt] created by Ji Sungbin on 21. 9. 2. 오후 10:21
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.model.commit

data class CommitContentItem(
    val patch: String,
    val filename: String,
    val additions: Int,
    val deletions: Int,
    val changes: Int,
    val rawUrl: String,
    val status: String,
    val htmlUrl: String,
)
