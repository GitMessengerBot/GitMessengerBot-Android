/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GitMessengerBot.kt] created by Ji Sungbin on 21. 6. 15. 오후 9:39.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot

import android.app.Application
import android.os.Process
import com.mocklets.pluto.Pluto
import com.mocklets.pluto.PlutoLog
import com.mocklets.pluto.modules.exceptions.ANRException
import com.mocklets.pluto.modules.exceptions.ANRListener
import dagger.hilt.android.HiltAndroidApp
import io.github.jisungbin.gitmessengerbot.common.core.NotificationUtil
import io.github.sungbin.gitmessengerbot.core.bot.Bot
import io.github.sungbin.gitmessengerbot.core.bot.debug.DebugStore
import io.github.sungbin.gitmessengerbot.core.setting.AppConfig
import kotlin.system.exitProcess
import timber.log.Timber

@HiltAndroidApp
class GitMessengerBot : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        NotificationUtil.createChannel(
            context = applicationContext,
            name = getString(R.string.notification_channel_name),
            description = getString(R.string.notification_channel_name)
        )

        Bot.scripts
        AppConfig.app
        DebugStore.items

        Pluto.initialize(applicationContext)
        Pluto.setANRListener(object : ANRListener {
            override fun onAppNotResponding(exception: ANRException) {
                exception.printStackTrace()
                println("AAA")
                PlutoLog.e("ANR", exception.threadStateMap)
            }
        })

        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            println("Catched Exception") // TODO: 데이터 저장 했다가 다음에 실행됐을 때 보여주기
            Process.killProcess(Process.myPid())
            exitProcess(10)
        }
    }
}
