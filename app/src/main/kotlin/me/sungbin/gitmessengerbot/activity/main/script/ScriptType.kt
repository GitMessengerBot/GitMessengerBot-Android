/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ScriptType.kt] created by Ji Sungbin on 21. 6. 19. 오후 11:03.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.script

object ScriptType {
    const val TypeScript = 0
    const val JavaScript = 1
    const val Python = 2
    const val Simple = 3 // 단자응
}

fun Int.toScriptType() = when (this) {
    0 -> "TypeScript"
    1 -> "JavaScript"
    2 -> "Python"
    3 -> "Simple"
    else -> throw Error("Unknown script type.")
}
