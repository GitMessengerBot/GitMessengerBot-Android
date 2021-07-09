/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [script.kt] created by Ji Sungbin on 21. 7. 9. 오후 11:14.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.util

import me.sungbin.gitmessengerbot.activity.main.script.ScriptItem
import me.sungbin.gitmessengerbot.activity.main.script.toScriptDefaultSource
import me.sungbin.gitmessengerbot.util.config.PathConfig

object Script {
    fun create(script: ScriptItem) {
        Storage.write(
            PathConfig.Script(script.name, script.lang),
            script.lang.toScriptDefaultSource()
        )
        Storage.write(PathConfig.ScriptData(script.name, script.lang), Json.toString(script))
    }

    fun remove(name: String, lang: Int) {
        Storage.remove(PathConfig.Script(name, lang))
    }

    fun getList(): List<ScriptItem> {
        val scripts = mutableListOf<ScriptItem>()
        repeat(4) { lang ->
            Storage.fileList(PathConfig.ScriptPath(lang)).filter { it.path.endsWith(".json") }
                .forEach { scriptDataFile ->
                    scripts.add(
                        Json.toModel(
                            Storage.read(scriptDataFile.path, null)!!,
                            ScriptItem::class
                        )
                    )
                }
        }
        return scripts
    }
}
