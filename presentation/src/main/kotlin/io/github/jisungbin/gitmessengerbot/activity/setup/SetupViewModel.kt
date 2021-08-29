/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [SetupViewModel.kt] created by Ji Sungbin on 21. 8. 29. 오후 7:22
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubGetUserInfoUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubRequestAouthTokenUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(
    private val githubGetUserInfoUseCase: GithubGetUserInfoUseCase,
    private val githubRequestAouthTokenUseCase: GithubRequestAouthTokenUseCase,
) : ViewModel() {

    fun githubRequestAouthToken(requestCode: String) = viewModelScope.launch {
        githubRequestAouthTokenUseCase.invoke(requestCode)
    }

    fun githubGetUserInfo(githubKey: String) = viewModelScope.launch {
        return@launch githubGetUserInfoUseCase.invoke(githubKey)
    }
}
