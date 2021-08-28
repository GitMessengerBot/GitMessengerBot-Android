/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ScriptItem.kt] created by Ji Sungbin on 21. 7. 9. 오전 2:34.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.bot.script

private typealias ScriptItems = List<ScriptItem>

data class ScriptItem(
    val id: Int,
    val name: String,
    val lang: Int,
    var power: Boolean,
    var compiled: Boolean,
    var lastRun: String,
)

fun ScriptItems.sorted() =
    sortedByDescending { it.name }.sortedByDescending { it.lang }.asReversed()

fun ScriptItems.getPowerOnScripts() = filter { it.power }

fun ScriptItems.getCompiledScripts() = filter { it.power && it.compiled }
