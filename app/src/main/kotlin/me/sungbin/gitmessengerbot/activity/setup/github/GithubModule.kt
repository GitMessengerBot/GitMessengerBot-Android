/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubModule.kt] created by Ji Sungbin on 21. 6. 15. 오후 9:50.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.setup.github

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import me.sungbin.gitmessengerbot.activity.setup.github.repo.GithubRepo
import me.sungbin.gitmessengerbot.activity.setup.github.repo.GithubRepoImpl
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object GithubModule {
    private const val BaseUrl = "https://api.github.com"

    @Provides
    @Singleton
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl(BaseUrl)
        .addConverterFactory(GsonConverterFactory.create())

    @Provides
    @Singleton
    fun provideRepo(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        retrofit: Retrofit.Builder
    ): GithubRepo = GithubRepoImpl(httpLoggingInterceptor, retrofit)
}
