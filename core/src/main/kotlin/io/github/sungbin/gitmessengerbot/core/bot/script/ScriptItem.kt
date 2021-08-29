/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ScriptItem.kt] created by Ji Sungbin on 21. 7. 9. 오전 2:34.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.bot.script

import io.github.jisungbin.gitmessengerbot.util.config.ScriptConfig
import io.github.jisungbin.gitmessengerbot.util.core.Storage
import io.github.jisungbin.gitmessengerbot.util.exception.CoreException
import io.github.jisungbin.gitmessengerbot.util.extension.toJsonString
import io.github.jisungbin.gitmessengerbot.util.script.ScriptLang
import io.github.sungbin.gitmessengerbot.core.setting.model.ScriptDefaultCode

private typealias ScriptItems = List<ScriptItem>

data class ScriptItem(
    val id: Int,
    val name: String,
    val lang: Int,
    var power: Boolean,
    var compiled: Boolean,
    var lastRun: String,
) {
    private fun getScriptDefaultCode(): String {
        val scriptDefaultCode = ScriptDefaultCode()
        return when (lang) {
            ScriptLang.JavaScript -> scriptDefaultCode.js
            ScriptLang.TypeScript -> scriptDefaultCode.ts
            ScriptLang.Python -> scriptDefaultCode.py
            ScriptLang.Simple -> scriptDefaultCode.sim
            else -> throw IndexOutOfBoundsException()
        }
    }

    val isRunnable = power && compiled

    fun add() {
        Storage.write(ScriptConfig.ScriptPath(name, lang), getScriptDefaultCode())
        Storage.write(ScriptConfig.ScriptDataPath(name, lang), toJsonString())
    }

    fun delete() {
        Storage.delete(ScriptConfig.ScriptPath(name, lang))
        Storage.delete(ScriptConfig.ScriptDataPath(name, lang))
    }

    fun getCode() =
        Storage.read(ScriptConfig.ScriptPath(name, lang), getScriptDefaultCode())
            ?: throw CoreException("The script's code cannot be null. (ScriptId: $id)")
}

fun ScriptItems.sorted() =
    sortedByDescending { it.name }.sortedByDescending { it.lang }.asReversed()

fun ScriptItems.search(scriptName: String) = filter { it.name.contains(scriptName) }
