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
import io.github.jisungbin.gitmessengerbot.activity.setup.mvi.BaseMviSetupSideEffect
import io.github.jisungbin.gitmessengerbot.activity.setup.mvi.SetupMviState
import io.github.jisungbin.gitmessengerbot.common.extension.doWhen
import io.github.jisungbin.gitmessengerbot.common.extension.toException
import io.github.jisungbin.gitmessengerbot.domain.github.model.user.GithubData
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubGetUserInfoUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubRequestAouthTokenUseCase
import javax.inject.Inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class SetupViewModel @Inject constructor(
    private val githubRequestAouthTokenUseCase: GithubRequestAouthTokenUseCase,
    private val githubGetUserInfoUseCase: GithubGetUserInfoUseCase,
) : ContainerHost<SetupMviState, BaseMviSetupSideEffect>, ViewModel() {

    override val container = container<SetupMviState, BaseMviSetupSideEffect>(SetupMviState())

    fun login(requestCode: String) = intent {
        githubRequestAouthTokenUseCase(
            requestCode = requestCode,
            coroutineScope = viewModelScope
        ).doWhen(
            onSuccess = { githubAouth ->
                var githubData = GithubData(aouthToken = githubAouth.token)
                githubGetUserInfoUseCase(
                    aouthToken = githubData.aouthToken,
                    coroutineScope = viewModelScope
                ).doWhen(
                    onSuccess = { userInfo ->
                        githubData = githubData.copy(
                            userName = userInfo.userName,
                            profileImageUrl = userInfo.profileImageUrl
                        )
                        postSideEffect(BaseMviSetupSideEffect.SaveData(githubData))
                        reduce {
                            state.copy(
                                loaded = true,
                                exception = null,
                                aouthToken = githubData.aouthToken,
                                userName = githubData.userName,
                                profileImageUrl = githubData.profileImageUrl
                            )
                        }
                    },
                    onFailure = { throwable ->
                        reduce {
                            state.copy(exception = throwable.toException())
                        }
                    }
                )
            },
            onFailure = { throwable ->
                reduce {
                    state.copy(exception = throwable.toException())
                }
            }
        )
    }
}
