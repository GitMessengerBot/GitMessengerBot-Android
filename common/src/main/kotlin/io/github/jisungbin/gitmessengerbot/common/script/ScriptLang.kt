/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ScriptLang.kt] created by Ji Sungbin on 21. 8. 28. 오후 3:53
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.common.script

import io.github.jisungbin.gitmessengerbot.common.exception.CommonException

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
    else -> throw CommonException("Unknown script type: $this")
}

fun Int.getScriptSuffix() = when (this) {
    ScriptLang.TypeScript -> "ts"
    ScriptLang.JavaScript -> "js"
    ScriptLang.Python -> "py"
    ScriptLang.Simple -> "sim"
    else -> throw CommonException("Unknown script type: $this")
}
