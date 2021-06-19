/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Repo.kt] created by Ji Sungbin on 21. 6. 19. 오후 11:55.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.kaven.model

import kotlin.random.Random

data class Repo(
    val id: Int = Random.nextInt(),
    val name: String,
    val owner: String, // github - username
    val subscriber: List<String> = listOf(),
    val reaction: Reaction = Reaction(),
    val comments: List<Comment> = listOf()
)
