/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ScriptItem.kt] created by Ji Sungbin on 21. 7. 9. 오전 2:34.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.script

data class ScriptItem(
    val id: Int,
    val name: String,
    val lang: Int, // ScriptType
    val power: Boolean,
    val compiled: Boolean,
    val lastRun: String
)
