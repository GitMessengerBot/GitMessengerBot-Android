/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [RetrofitModule.kt] created by Ji Sungbin on 21. 8. 30. 오후 5:45
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.di.module

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jisungbin.gitmessengerbot.common.constant.GithubConstant
import io.github.jisungbin.gitmessengerbot.common.core.Storage
import io.github.jisungbin.gitmessengerbot.common.exception.PresentationException
import io.github.jisungbin.gitmessengerbot.common.extension.toModel
import io.github.jisungbin.gitmessengerbot.di.qualifier.AouthRetrofit
import io.github.jisungbin.gitmessengerbot.di.qualifier.SignedRetrofit
import io.github.jisungbin.gitmessengerbot.di.qualifier.UserRetrofit
import io.github.jisungbin.gitmessengerbot.domain.github.model.user.GithubData
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private val mapper = ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
        .registerKotlinModule()

    private class AuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var builder = chain.request().newBuilder()
            val githubData: GithubData = Storage.read(GithubConstant.DataPath, null)?.toModel()
                ?: throw PresentationException("GithubConfig.DataPath 값이 null 이에요.")
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
        .addConverterFactory(JacksonConverterFactory.create(mapper))

    @Provides
    @SignedRetrofit
    @Singleton
    fun provideSignedRetrofit(loggingInterceptor: HttpLoggingInterceptor): Retrofit =
        Retrofit.Builder()
            .baseUrl(GithubConstant.BaseApiUrl)
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .client(getInterceptor(loggingInterceptor, AuthInterceptor()))
            .build()

    @Provides
    @UserRetrofit
    @Singleton
    fun provideUserRetrofit(): Retrofit.Builder =
        buildRetrofitWithoutClient(baseUrl = GithubConstant.BaseApiUrl)

    @Provides
    @AouthRetrofit
    @Singleton
    fun provideAouthRetrofit(): Retrofit.Builder =
        buildRetrofitWithoutClient(baseUrl = GithubConstant.BaseUrl)
}
