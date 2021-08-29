/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubModule.kt] created by Ji Sungbin on 21. 6. 15. 오후 9:50.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.setup.repo.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubRepository
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubGetUserInfoUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubRequestAouthTokenUseCase

@Module
@InstallIn(ActivityComponent::class)
object UseCaseModule {
    @Provides
    @ActivityScoped
    fun provideRequestAouthTokenUseCase(githubRepository: GithubRepository) =
        GithubRequestAouthTokenUseCase(githubRepository)

    @Provides
    @ActivityScoped
    fun provideGetUserInfoUseCase(githubRepository: GithubRepository) =
        GithubGetUserInfoUseCase(githubRepository)
}
