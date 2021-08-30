/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [RetrofitModule.kt] created by Ji Sungbin on 21. 8. 30. 오후 5:45
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.debug.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import io.github.jisungbin.gitmessengerbot.activity.setup.model.GithubData
import io.github.jisungbin.gitmessengerbot.common.config.GithubConfig
import io.github.jisungbin.gitmessengerbot.common.core.Storage
import io.github.jisungbin.gitmessengerbot.common.exception.PresentationException
import io.github.jisungbin.gitmessengerbot.common.extension.toModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityComponent::class)
object RetrofitModule {
    private class AuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var builder = chain.request().newBuilder()
            val githubData: GithubData = Storage.read(GithubConfig.DataPath, null)?.toModel()
                ?: throw PresentationException("GithubConfig.DataPath value is cannot be null.")
            builder = builder
                .addHeader("Authorization", "token ${githubData.token}")
                .addHeader("Accept", "application/json")
            return chain.proceed(builder.build())
        }
    }

    private fun getInterceptor(vararg interceptors: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        for (interceptor in interceptors) builder.addInterceptor(interceptor)
        return builder.build()
    }

    @Provides
    @ActivityScoped
    fun provideRetrofit(loggingInterceptor: HttpLoggingInterceptor) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(getInterceptor(loggingInterceptor, AuthInterceptor()))
        .baseUrl(GithubConfig.BaseApiUrl)
        .build()
}
