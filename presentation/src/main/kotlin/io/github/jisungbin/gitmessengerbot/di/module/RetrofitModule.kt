/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [RetrofitModule.kt] created by Ji Sungbin on 21. 8. 30. 오후 5:45
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.di.module

import com.mocklets.pluto.PlutoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jisungbin.gitmessengerbot.activity.setup.model.GithubData
import io.github.jisungbin.gitmessengerbot.common.config.GithubConfig
import io.github.jisungbin.gitmessengerbot.common.core.Storage
import io.github.jisungbin.gitmessengerbot.common.exception.PresentationException
import io.github.jisungbin.gitmessengerbot.common.extension.toModel
import io.github.jisungbin.gitmessengerbot.di.qualifier.AouthRetrofit
import io.github.jisungbin.gitmessengerbot.di.qualifier.SignedRetrofit
import io.github.jisungbin.gitmessengerbot.di.qualifier.UserRetrofit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Suppress("HasPlatformType")
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private class AuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var builder = chain.request().newBuilder()
            val githubData: GithubData = Storage.read(GithubConfig.DataPath, null)?.toModel()
                ?: throw PresentationException("GithubConfig.DataPath value is cannot be null.")
            builder = builder
                .addHeader("Authorization", "token ${githubData.aouthToken}")
                .addHeader("Accept", "application/json")
            return chain.proceed(builder.build())
        }
    }

    private fun getInterceptor(vararg interceptors: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        for (interceptor in interceptors) builder.addInterceptor(interceptor)
        return builder.build()
    }

    private fun buildRetrofitWithoutClient(baseUrl: String) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())

    @Provides
    @SignedRetrofit
    @Singleton
    fun provideSignedRetrofit(loggingInterceptor: HttpLoggingInterceptor) = Retrofit.Builder()
        .baseUrl(GithubConfig.BaseApiUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getInterceptor(loggingInterceptor, AuthInterceptor(), PlutoInterceptor()))
        .build()

    @Provides
    @UserRetrofit
    @Singleton
    fun provideUserRetrofit() = buildRetrofitWithoutClient(baseUrl = GithubConfig.BaseApiUrl)

    @Provides
    @AouthRetrofit
    @Singleton
    fun provideAouthRetrofit() = buildRetrofitWithoutClient(baseUrl = GithubConfig.BaseUrl)
}
