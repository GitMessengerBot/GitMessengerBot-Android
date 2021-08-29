/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [RepositoryModule.kt] created by Ji Sungbin on 21. 8. 29. 오후 7:17
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.setup.repo.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import io.github.jisungbin.gitmessengerbot.activity.setup.repo.qualifier.AouthRetrofit
import io.github.jisungbin.gitmessengerbot.activity.setup.repo.qualifier.UserRetrofit
import io.github.jisungbin.gitmessengerbot.data.github.repository.GithubRepositoryImpl
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubRepository
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object RepositoryModule {
    @Provides
    @ActivityScoped
    fun provideGithubRepository(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @UserRetrofit userRetrofit: Retrofit.Builder,
        @AouthRetrofit aouthRetrofit: Retrofit.Builder,
    ): GithubRepository = GithubRepositoryImpl(httpLoggingInterceptor, userRetrofit, aouthRetrofit)
}
