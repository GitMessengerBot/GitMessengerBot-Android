package com.sungbin.gitkakaobot.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import javax.inject.Named
import javax.inject.Singleton


/**
 * Created by SungBin on 2020-08-24.
 */

@Module
@InstallIn(ApplicationComponent::class)
class BeautifyClient {

    @Named("pretty")
    @Provides
    @Singleton
    fun pretty() = instance("https://www.prettifyjs.net/")

    @Named("minify")
    @Provides
    @Singleton
    fun minify() = instance("https://javascript-minifier.com/")

    private fun instance(baseUrl: String) = Retrofit.Builder()
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(baseUrl)
        .build()
}