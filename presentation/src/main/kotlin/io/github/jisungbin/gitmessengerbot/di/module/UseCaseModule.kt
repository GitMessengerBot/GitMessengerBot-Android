/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [UseCaseModule.kt] created by Ji Sungbin on 21. 8. 30. 오후 5:44
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubCommitRepository
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubRepoRepository
import io.github.jisungbin.gitmessengerbot.domain.github.repo.GithubUserRepository
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GetCommitContentUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GetCommitHistoryUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubCreateRepoUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubGetFileContentUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubGetUserInfoUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubRequestAouthTokenUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubUpdateFileUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideGithubGetFileContentUseCase(githubRepoRepository: GithubRepoRepository) =
        GithubGetFileContentUseCase(githubRepoRepository)

    @Provides
    @ViewModelScoped
    fun provideGithubUpdateFileUseCase(githubRepoRepository: GithubRepoRepository) =
        GithubUpdateFileUseCase(githubRepoRepository)

    @Provides
    @ViewModelScoped
    fun provideGithubCreateRepoUseCase(githubRepoRepository: GithubRepoRepository) =
        GithubCreateRepoUseCase(githubRepoRepository)

    @Provides
    @ViewModelScoped
    fun provideRequestAouthTokenUseCase(githubUserRepository: GithubUserRepository) =
        GithubRequestAouthTokenUseCase(githubUserRepository)

    @Provides
    @ViewModelScoped
    fun provideGetUserInfoUseCase(githubUserRepository: GithubUserRepository) =
        GithubGetUserInfoUseCase(githubUserRepository)

    @Provides
    @ViewModelScoped
    fun provideGetCommitContentUseCase(githubCommitRepository: GithubCommitRepository) =
        GetCommitContentUseCase(githubCommitRepository)

    @Provides
    @ViewModelScoped
    fun provideGetCommitHistoryUseCase(githubCommitRepository: GithubCommitRepository) =
        GetCommitHistoryUseCase(githubCommitRepository)
}
