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
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object Ts2JsModule {
    private const val BaseUrl = "https://api.extendsclass.com"

    private fun getInterceptor(vararg interceptors: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        for (interceptor in interceptors) builder.addInterceptor(interceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(httpLoggingInterceptor: HttpLoggingInterceptor) = Retrofit.Builder()
        .baseUrl(BaseUrl)
        .client(getInterceptor(httpLoggingInterceptor))
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideRepo(retrofit: Retrofit): Ts2JsRepo = Ts2JsRepoImpl(retrofit)
}
