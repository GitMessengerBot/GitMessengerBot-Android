package com.sungbin.gitkakaobot.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


/**
 * Created by SungBin on 2020-08-23.
 */

@Module
@InstallIn(ApplicationComponent::class)
class GithubClient {

    private val BASE_URL = "https://github.com/"

    @Named("github")
    @Provides
    @Singleton
    fun instance() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build()
}