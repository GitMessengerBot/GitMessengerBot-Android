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
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.leakcanary2.FlipperLeakListener
import com.facebook.flipper.plugins.leakcanary2.LeakCanary2FlipperPlugin
import com.facebook.soloader.SoLoader
import dagger.hilt.android.HiltAndroidApp
import io.github.jisungbin.erratum.Erratum
import io.github.jisungbin.gitmessengerbot.common.core.NotificationUtil
import io.github.jisungbin.logeukes.Logeukes
import io.github.sungbin.gitmessengerbot.core.bot.Bot
import io.github.sungbin.gitmessengerbot.core.bot.debug.DebugStore
import io.github.sungbin.gitmessengerbot.core.setting.AppConfig
import leakcanary.LeakCanary

@HiltAndroidApp
class GitMessengerBot : Application() {
    override fun onCreate() {
        super.onCreate()
        Erratum.setup(this) // todo: 디버그 모드 아닐땐 에러 스택 보여주면 안됨

        if (BuildConfig.DEBUG) {
            LeakCanary.config = LeakCanary.config.copy(
                onHeapAnalyzedListener = FlipperLeakListener()
            )

            SoLoader.init(this, false)
            Logeukes.setup()

            if (FlipperUtils.shouldEnableFlipper(this)) {
                AndroidFlipperClient.getInstance(this).apply {
                    addPlugin(CrashReporterPlugin.getInstance())
                    addPlugin(
                        InspectorFlipperPlugin(
                            this@GitMessengerBot,
                            DescriptorMapping.withDefaults()
                        )
                    )
                    addPlugin(LeakCanary2FlipperPlugin())
                }.start()
            }
        }

        NotificationUtil.createChannel(
            context = applicationContext,
            name = getString(R.string.notification_channel_name),
            description = getString(R.string.notification_channel_name)
        )

        Bot.scripts
        AppConfig.app
        DebugStore.items
    }
}
