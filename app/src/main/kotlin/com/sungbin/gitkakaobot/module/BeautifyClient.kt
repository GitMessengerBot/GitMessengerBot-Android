package com.sungbin.gitkakaobot.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.jsoup.Jsoup
import javax.inject.Named
import javax.inject.Singleton


/**
 * Created by SungBin on 2020-08-24.
 */

@Module
@InstallIn(ApplicationComponent::class)
class BeautifyClient {

    @Provides
    @Singleton
    @Named("pretty")
    fun pretty() = instance("https://amp.prettifyjs.net")

    @Provides
    @Singleton
    @Named("minify")
    fun minify() = instance("https://javascript-minifier.com/raw")

    private fun instance(baseUrl: String) = Jsoup.connect(baseUrl)
        .ignoreContentType(true)
        .ignoreHttpErrors(true)!!
}