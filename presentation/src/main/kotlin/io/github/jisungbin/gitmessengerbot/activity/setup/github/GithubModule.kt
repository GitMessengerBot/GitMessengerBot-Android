/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubModule.kt] created by Ji Sungbin on 21. 6. 15. 오후 9:50.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.setup.github

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jisungbin.gitmessengerbot.activity.setup.github.qualifier.AouthRetrofit
import io.github.jisungbin.gitmessengerbot.activity.setup.github.qualifier.UserRetrofit
import io.github.jisungbin.gitmessengerbot.domain.repository.github.GithubRepository
import io.github.jisungbin.gitmessengerbot.data.github.repository.GithubRepositoryImpl
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object GithubModule {
    private fun retrofit(baseUrl: String) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())

    @UserRetrofit
    @Provides
    @Singleton
    fun provideUserRetrofit() = retrofit("https://api.github.com")

    @AouthRetrofit
    @Provides
    @Singleton
    fun provideAouthRetrofit() = retrofit("https://github.com")

    @Provides
    @Singleton
    fun provideRepo(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @UserRetrofit userRetrofit: Retrofit.Builder,
        @AouthRetrofit aouthRetrofit: Retrofit.Builder
    ): GithubRepository = GithubRepositoryImpl(httpLoggingInterceptor, userRetrofit, aouthRetrofit)
}
