/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [BeautifyModule.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:11.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.editor.beautify

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import me.sungbin.gitmessengerbot.activity.main.editor.beautify.repo.BeautifyRepo
import me.sungbin.gitmessengerbot.activity.main.editor.beautify.repo.BeautifyRepoImpl
import org.jsoup.Connection
import org.jsoup.Jsoup

@Module
@InstallIn(SingletonComponent::class)
object BeautifyModule {

    @Provides
    @Singleton
    @Named("pretty")
    fun providePretty() = instance("https://amp.prettifyjs.net")

    @Provides
    @Singleton
    @Named("minify")
    fun provideMinify() = instance("https://javascript-minifier.com/raw")

    private fun instance(baseUrl: String): Connection = Jsoup.connect(baseUrl)
        .ignoreContentType(true)
        .ignoreHttpErrors(true)

    @Provides
    @Singleton
    fun provideRepo(
        @Named("pretty") pretty: Connection,
        @Named("minify") minify: Connection
    ): BeautifyRepo = BeautifyRepoImpl(pretty = pretty, minify = minify)
}