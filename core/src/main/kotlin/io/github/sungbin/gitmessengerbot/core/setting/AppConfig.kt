/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [AppConfig.kt] created by Ji Sungbin on 21. 8. 29. 오후 9:19
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.jisungbin.gitmessengerbot.util.config.PathConfig
import io.github.jisungbin.gitmessengerbot.util.config.ScriptConfig
import io.github.jisungbin.gitmessengerbot.util.core.Storage
import io.github.jisungbin.gitmessengerbot.util.exception.CoreException
import io.github.jisungbin.gitmessengerbot.util.extension.toModel
import io.github.sungbin.gitmessengerbot.core.bot.StackManager
import io.github.sungbin.gitmessengerbot.core.setting.model.App

object AppConfig {
    private val _app = MutableLiveData(loadApp())

    val app: LiveData<App> get() = _app
    val appValue = app.value ?: throw CoreException("AppConfig.app value is null.")

    val canUseEval: Boolean get() = StackManager.v8[ScriptConfig.EvalId] != null

    fun update(update: (App) -> App) {
        val value = _app.value ?: throw CoreException("AppConfig.app value is null.")
        _app.value = update(value)
    }

    private fun loadApp() = Storage.read(PathConfig.AppData, null)?.toModel() ?: App()
}
