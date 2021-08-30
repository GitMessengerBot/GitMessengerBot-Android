/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [DebugViewModel.kt] created by Ji Sungbin on 21. 8. 30. 오후 5:26
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.debug

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubCreateRepoUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubGetFileContentUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubUpdateFileUseCase
import javax.inject.Inject

@HiltViewModel
class DebugViewModel @Inject constructor(
    private val githubGetFileContentUseCase: GithubGetFileContentUseCase,
    private val githubUpdateFileUseCase: GithubUpdateFileUseCase,
    private val githubCreateRepoUseCase: GithubCreateRepoUseCase,
) : ViewModel() {
    fun
}
