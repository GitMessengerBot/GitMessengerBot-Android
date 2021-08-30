/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [UseCaseModule.kt] created by Ji Sungbin on 21. 8. 30. 오후 5:44
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.editor.js.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubRepoRepository
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubCreateRepoUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubGetFileContentUseCase

@Module
@InstallIn(ActivityComponent::class)
object UseCaseModule {
    @Provides
    @ActivityScoped
    fun provideGithubGetFileContentUseCase(githubRepoRepository: GithubRepoRepository) =
        GithubGetFileContentUseCase(githubRepoRepository)

    @Provides
    @ActivityScoped
    fun provideGithubUpdateFileUseCase(githubRepoRepository: GithubRepoRepository) =
        GithubUpdateFileUsecase(githubRepoRepository)

    @Provides
    @ActivityScoped
    fun provideGithubCreateRepoUseCase(githubRepoRepository: GithubRepoRepository) =
        GithubCreateRepoUseCase(githubRepoRepository)
}
