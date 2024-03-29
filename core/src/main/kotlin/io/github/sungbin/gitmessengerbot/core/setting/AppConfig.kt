/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [AppConfig.kt] created by Ji Sungbin on 21. 8. 29. 오후 9:19
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.setting

import io.github.jisungbin.gitmessengerbot.common.constant.PathConstant
import io.github.jisungbin.gitmessengerbot.common.constant.ScriptConstant
import io.github.jisungbin.gitmessengerbot.common.core.Storage
import io.github.jisungbin.gitmessengerbot.common.extension.toJsonString
import io.github.jisungbin.gitmessengerbot.common.extension.toModel
import io.github.sungbin.gitmessengerbot.core.bot.StackManager
import io.github.sungbin.gitmessengerbot.core.setting.model.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object AppConfig {
    private val _app = MutableStateFlow(loadApp())
    val app = _app.asStateFlow()
    val appValue: App get() = app.value

    val evalUsable: Boolean get() = StackManager.v8[ScriptConstant.EvalId] != null

    fun update(update: (App) -> App) {
        val value = update(appValue)
        Storage.write(PathConstant.AppData, value.toJsonString())
        _app.value = value
    }

    private fun loadApp() = Storage.read(PathConstant.AppData, null)?.toModel() ?: App()
}
