/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [RepositoryModule.kt] created by Ji Sungbin on 21. 8. 30. 오후 5:58
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.debug.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import io.github.jisungbin.gitmessengerbot.data.github.repo.GithubRepoRepositoryImpl
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubRepoRepository
import retrofit2.Retrofit

@Module
@InstallIn(ActivityComponent::class)
object RepositoryModule {
    @Provides
    @ActivityScoped
    fun provideGithubRepository(retrofit: Retrofit): GithubRepoRepository =
        GithubRepoRepositoryImpl(retrofit)
}
