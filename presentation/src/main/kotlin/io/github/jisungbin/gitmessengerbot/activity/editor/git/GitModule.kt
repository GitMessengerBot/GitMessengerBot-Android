/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubClient.kt] created by Ji Sungbin on 21. 7. 13. 오전 1:11.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.editor.git

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jisungbin.gitmessengerbot.activity.editor.git.repo.GitRepo
import io.github.jisungbin.gitmessengerbot.activity.editor.git.repo.GitRepoImpl
import io.github.jisungbin.gitmessengerbot.domain.model.github.GithubData
import io.github.jisungbin.gitmessengerbot.util.Json
import io.github.jisungbin.gitmessengerbot.util.Storage
import io.github.jisungbin.gitmessengerbot.util.config.StringConfig
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object GitModule {
    private const val BaseUrl = "https://api.github.com"

    private class AuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var builder = chain.request().newBuilder()
            return try {
                val githubJson = Storage.read(StringConfig.GithubData, null)!!
                val githubData = Json.toModel(githubJson, GithubData::class)
                builder = builder
                    .addHeader("Authorization", "token ${githubData.token}")
                    .addHeader("Accept", "application/json")
                chain.proceed(builder.build())
            } catch (ignored: Exception) {
                chain.proceed(builder.build())
            }
        }
    }

    private fun getInterceptor(vararg interceptors: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        for (interceptor in interceptors) builder.addInterceptor(interceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(loggingInterceptor: HttpLoggingInterceptor) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(getInterceptor(loggingInterceptor, AuthInterceptor()))
        .baseUrl(BaseUrl)
        .build()

    @Provides
    @Singleton
    fun provideRepo(retrofit: Retrofit): GitRepo = GitRepoImpl(retrofit)
}
