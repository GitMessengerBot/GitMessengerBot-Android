/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ScriptType.kt] created by Ji Sungbin on 21. 6. 19. 오후 11:03.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.main.script

import io.github.jisungbin.gitmessengerbot.bot.Bot

object ScriptLang {
    const val TypeScript = 0
    const val JavaScript = 1
    const val Python = 2 // todo
    const val Simple = 3 // 단자응
}

fun Int.toScriptLangName() = when (this) {
    ScriptLang.TypeScript -> "TypeScript"
    ScriptLang.JavaScript -> "JavaScript"
    ScriptLang.Python -> "Python"
    ScriptLang.Simple -> "Simple"
    else -> throw Error("Unknown script type.")
}

fun Int.toScriptSuffix() = when (this) {
    ScriptLang.TypeScript -> "ts"
    ScriptLang.JavaScript -> "js"
    ScriptLang.Python -> "py"
    ScriptLang.Simple -> "sim"
    else -> throw Error("Unknown script type.")
}

fun Int.toScriptDefaultSource() = when (this) {
    ScriptLang.TypeScript -> Bot.app.value.scriptDefaultCode.ts
    ScriptLang.JavaScript -> Bot.app.value.scriptDefaultCode.js
    ScriptLang.Python -> Bot.app.value.scriptDefaultCode.python
    ScriptLang.Simple -> Bot.app.value.scriptDefaultCode.sim
    else -> throw Error("Unknown script type.")
}
