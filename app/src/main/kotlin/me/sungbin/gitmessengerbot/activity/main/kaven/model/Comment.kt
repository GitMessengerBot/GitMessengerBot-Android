/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Comment.kt] created by Ji Sungbin on 21. 6. 19. 오후 11:58.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.kaven.model

data class Comment(
    val owner: String,
    val content: String,
    val reaction: Reaction
)
