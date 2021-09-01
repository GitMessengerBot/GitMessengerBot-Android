/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [CommitItem.kt] created by Ji Sungbin on 21. 9. 1. 오후 9:21
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github.model.commit

data class CommitItem(
    val commiter: Commiter,
    val sha: String,
    val message: String,
)
