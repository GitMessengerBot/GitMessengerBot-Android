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
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.github.jisungbin.gitmessengerbot.data.github.repo.GithubRepoRepositoryImpl
import io.github.jisungbin.gitmessengerbot.data.github.repo.GithubUserRepositoryImpl
import io.github.jisungbin.gitmessengerbot.di.qualifier.AouthRetrofit
import io.github.jisungbin.gitmessengerbot.di.qualifier.SignedRetrofit
import io.github.jisungbin.gitmessengerbot.di.qualifier.UserRetrofit
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubRepoRepository
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubUserRepository
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object RepoModule {
    @Provides
    @ViewModelScoped
    fun provideGithubRepoRepository(@SignedRetrofit retrofit: Retrofit): GithubRepoRepository =
        GithubRepoRepositoryImpl(retrofit)

    @Provides
    @ViewModelScoped
    fun provideGithubUserRepository(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @UserRetrofit userRetrofit: Retrofit.Builder,
        @AouthRetrofit aouthRetrofit: Retrofit.Builder,
    ): GithubUserRepository =
        GithubUserRepositoryImpl(httpLoggingInterceptor, userRetrofit, aouthRetrofit)
}
