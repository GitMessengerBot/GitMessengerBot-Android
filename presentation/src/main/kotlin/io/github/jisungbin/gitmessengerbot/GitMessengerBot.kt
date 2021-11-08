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
import com.mocklets.pluto.Pluto
import dagger.hilt.android.HiltAndroidApp
import io.github.jisungbin.erratum.Erratum
import io.github.jisungbin.gitmessengerbot.common.core.NotificationUtil
import io.github.jisungbin.logeukes.Logeukes
import io.github.sungbin.gitmessengerbot.core.bot.Bot
import io.github.sungbin.gitmessengerbot.core.bot.debug.DebugStore
import io.github.sungbin.gitmessengerbot.core.setting.AppConfig

@HiltAndroidApp
class GitMessengerBot : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Logeukes.setup()
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
        Erratum.setup(this)
    }
}
