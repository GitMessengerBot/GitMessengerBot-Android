/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [NotifiactionService.kt] created by Ji Sungbin on 21. 7. 16. 오전 1:55.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.github.jisungbin.gitmessengerbot.common.config.IntentConfig
import io.github.jisungbin.gitmessengerbot.common.extension.toast
import io.github.sungbin.gitmessengerbot.core.CoreResult
import io.github.sungbin.gitmessengerbot.core.Injection
import io.github.sungbin.gitmessengerbot.core.R
import io.github.sungbin.gitmessengerbot.core.bot.Bot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotifiactionService : BroadcastReceiver() {

    private val scriptCompiler = Injection.Compiler.provide

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.getStringExtra(IntentConfig.NotificationAction)) {
            IntentConfig.BotPowerToggle -> {
                // TODO
                /*if (Bot.app.value.power.value) {
                    context!!.startService(Intent(context, BackgroundService::class.java))
                } else {
                    context!!.stopService(Intent(context, BackgroundService::class.java))
                }
                Bot.scriptDataSaveAndUpdate(Bot.app.value.copy(power = mutableStateOf(!Bot.app.value.power.value)))*/
            }
            IntentConfig.BotAllRecompile -> {
                CoroutineScope(Dispatchers.Default).launch {
                    toast(
                        context!!,
                        context.getString(R.string.service_notification_toast_running_scripts_recompile)
                    )
                    Bot.getRunnableScripts().forEach { script ->
                        scriptCompiler.process(context, script).collect { result ->
                            when (result) {
                                is CoreResult.Fail -> {
                                    toast(
                                        context,
                                        context.getString(
                                            R.string.service_notification_toast_compile_error,
                                            script.name,
                                            result.exception.message
                                        )
                                    )
                                }
                                is CoreResult.Success -> {
                                    toast(
                                        context,
                                        context.getString(R.string.service_notification_toast_recompile_done)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
