/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [NotifiactionService.kt] created by Ji Sungbin on 21. 7. 16. 오전 1:55.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.activity.main.script.compiler.CompileResult
import me.sungbin.gitmessengerbot.activity.main.script.compiler.repo.ScriptCompiler
import me.sungbin.gitmessengerbot.bot.Bot
import me.sungbin.gitmessengerbot.util.config.StringConfig
import me.sungbin.gitmessengerbot.util.extension.toast

@AndroidEntryPoint
class NotifiactionService : BroadcastReceiver() {

    @Inject
    lateinit var scriptCompiler: ScriptCompiler

    override fun onReceive(context: Context?, intent: Intent?) {
        var power = Bot.app.value.power

        when (intent?.getStringExtra(StringConfig.IntentNotificationAction)) {
            StringConfig.IntentBotPowerToggle -> {
                if (power) {
                    context!!.startService(Intent(context, BackgroundService::class.java))
                } else {
                    context!!.stopService(Intent(context, BackgroundService::class.java))
                }
                power = !power
                Bot.save(Bot.app.value.copy(power = power))
            }
            StringConfig.IntentBotRecompile -> {
                toast(context!!, context.getString(R.string.service_toast_compile_running))
                CoroutineScope(Dispatchers.Default).launch {
                    Bot.getPowerOnScripts().forEach { script ->
                        scriptCompiler.process(context, script).collect { result ->
                            if (result is CompileResult.Error) {
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
