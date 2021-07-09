/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Script.kt] created by Ji Sungbin on 21. 7. 9. 오후 11:14.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.util

import me.sungbin.gitmessengerbot.activity.main.script.toScriptDefaultSource
import me.sungbin.gitmessengerbot.util.config.PathConfig

object Script {
    fun create(name: String, lang: Int) {
        Storage.write(PathConfig.Script(name, lang), lang.toScriptDefaultSource())
    }

    fun remove(name: String, lang: Int) {
        Storage.remove(PathConfig.Script(name, lang))
    }
}
