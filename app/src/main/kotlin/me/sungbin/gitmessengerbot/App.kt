/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [App.kt] created by Ji Sungbin on 21. 7. 11. 오전 4:02.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot

import me.sungbin.gitmessengerbot.activity.main.script.ScriptLang
import me.sungbin.gitmessengerbot.util.config.StringConfig

data class App(
    var power: Boolean = false,
    var defaultLang: Int = ScriptLang.TypeScript,
    var evalMode: Boolean = false,
    var defaultBranch: String = StringConfig.GitDefaultBranch
)
