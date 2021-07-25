/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [NotifiactionService.kt] created by Ji Sungbin on 21. 7. 16. 오전 1:55.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.AndroidEntryPoint
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.activity.main.script.compiler.repo.ScriptCompiler
import io.github.jisungbin.gitmessengerbot.bot.Bot
import io.github.jisungbin.gitmessengerbot.repo.Result
import io.github.jisungbin.gitmessengerbot.util.config.StringConfig
import io.github.jisungbin.gitmessengerbot.util.extension.toast
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotifiactionService : BroadcastReceiver() {

    @Inject
    lateinit var scriptCompiler: ScriptCompiler

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.getStringExtra(StringConfig.IntentNotificationAction)) {
            StringConfig.IntentBotPowerToggle -> {
                if (Bot.app.value.power.value) {
                    context!!.startService(Intent(context, BackgroundService::class.java))
                } else {
                    context!!.stopService(Intent(context, BackgroundService::class.java))
                }
                Bot.saveAndUpdate(Bot.app.value.copy(power = mutableStateOf(!Bot.app.value.power.value)))
            }
            StringConfig.IntentBotRecompile -> {
                toast(context!!, context.getString(R.string.service_toast_active_scripts_recompile))
                CoroutineScope(Dispatchers.Default).launch {
                    Bot.getPowerOnScripts().forEach { script ->
                        scriptCompiler.process(context, script).collect { result ->
                            if (result is Result.Fail) {
                                toast(
                                    context,
                                    context.getString(
                                        R.string.service_toast_compile_error,
                                        script.name,
                                        result.exception.message
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
