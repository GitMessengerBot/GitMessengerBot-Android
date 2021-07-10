/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Ts2JsModule.kt] created by Ji Sungbin on 21. 7. 10. 오전 7:27.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.script.ts2js

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.jsoup.Jsoup

@Module
@InstallIn(SingletonComponent::class)
object Ts2JsModule {
    private const val BaseUrl = "https://api.extendsclass.com/convert/typescript/javascript"
    private val jsoup = Jsoup.connect(BaseUrl)

    @Provides
    @Singleton
    fun provideRepo(): Ts2JsRepo = Ts2JsRepoImpl(jsoup)
}
