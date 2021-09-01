/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [RepositoryModule.kt] created by Ji Sungbin on 21. 8. 30. 오후 5:58
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jisungbin.gitmessengerbot.data.github.repo.GithubRepoRepositoryImpl
import io.github.jisungbin.gitmessengerbot.data.github.repo.GithubUserRepositoryImpl
import io.github.jisungbin.gitmessengerbot.di.qualifier.AouthRetrofit
import io.github.jisungbin.gitmessengerbot.di.qualifier.SignedRetrofit
import io.github.jisungbin.gitmessengerbot.di.qualifier.UserRetrofit
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubRepoRepository
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubUserRepository
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    @Singleton
    fun provideGithubRepoRepository(@SignedRetrofit retrofit: Retrofit): GithubRepoRepository =
        GithubRepoRepositoryImpl(retrofit = retrofit)

    @Provides
    @Singleton
    fun provideGithubUserRepository(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @UserRetrofit userRetrofit: Retrofit.Builder,
        @AouthRetrofit aouthRetrofit: Retrofit.Builder,
    ): GithubUserRepository = GithubUserRepositoryImpl(
        httpLoggingInterceptor = httpLoggingInterceptor,
        userRetrofit = userRetrofit,
        aouthRetrofit = aouthRetrofit
    )
}
