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
import me.sungbin.gitmessengerbot.activity.main.script.ts2js.repo.Ts2JsRepo
import me.sungbin.gitmessengerbot.activity.main.script.ts2js.repo.Ts2JsRepoImpl
import org.jsoup.Jsoup

@Module
@InstallIn(SingletonComponent::class)
object Ts2JsModule {
    private const val BaseUrl = "https://api.extendsclass.com/convert/typescript/javascript"
    private val jsoup = Jsoup.connect(BaseUrl)

    /* note */
    // 아마... retrofit하고 jsoup하고 호출 방식이 다름 (나 중딩때 스택오버플에서 봤음! 기억 남 이건!!)
    // retrofit이 server-side, client-side 둘 중 하나고, jsoup은 retrofit의 반대 방식
    // retrofit의 방식으로 요청하면 응답이 비정상적으로 옴 -> 반대 방식인 jsoup으로 요청

    @Provides
    @Singleton
    fun provideRepo(): Ts2JsRepo = Ts2JsRepoImpl(jsoup)
}
