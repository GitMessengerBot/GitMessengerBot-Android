/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ScriptType.kt] created by Ji Sungbin on 21. 6. 19. 오후 11:03.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.script

object ScriptLang {
    const val TypeScript = 0
    const val JavaScript = 1
    const val Python = 2 // todo
    const val Simple = 3 // 단자응
}

fun Int.toScriptLangName() = when (this) {
    0 -> "TypeScript"
    1 -> "JavaScript"
    2 -> "Python"
    3 -> "Simple"
    else -> throw Error("Unknown script type.")
}

fun Int.toScriptSuffix() = when (this) {
    0 -> "ts"
    1 -> "js"
    2 -> "py"
    3 -> "sim"
    else -> throw Error("Unknown script type.")
}

fun Int.toScriptDefaultSource() = when (this) {
    0 -> "const onMessage = (room: string, message: string, sender: string, isGroupChat: boolean, profileImageBase64: string) => {\n}"
    1 -> "const onMessage = (room, message, sender, isGroupChat, profileImageBase64) => {\n}"
    2 -> "def onMessage(self, room, message, sender, isGroupChat, profileImageBase64):\n    "
    3 -> "sim"
    else -> throw Error("Unknown script type.")
}
