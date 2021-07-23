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
import dagger.hilt.android.HiltAndroidApp
import io.github.jisungbin.gitmessengerbot.util.NotificationUtil
import io.github.jisungbin.gitmessengerbot.util.Util
import kotlin.system.exitProcess

@HiltAndroidApp
class GitMessengerBot : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationUtil.createChannel(
            context = applicationContext,
            name = getString(R.string.notification_channel_name),
            description = getString(R.string.notification_channel_name)
        )

        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            Util.error(applicationContext, "알 수 없는 에러 발생\n\n$throwable")
            exitProcess(0)
        }
    }
}
