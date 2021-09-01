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
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jisungbin.gitmessengerbot.domain.github.model.user.GithubData
import io.github.jisungbin.gitmessengerbot.common.config.GithubConfig
import io.github.jisungbin.gitmessengerbot.common.core.Storage
import io.github.jisungbin.gitmessengerbot.common.exception.CoreException
import io.github.jisungbin.gitmessengerbot.common.extension.toJsonString
import io.github.jisungbin.gitmessengerbot.domain.github.doWhen
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubGetUserInfoUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubRequestAouthTokenUseCase
import io.github.jisungbin.gitmessengerbot.util.RequestResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(
    private val githubRequestAouthTokenUseCase: GithubRequestAouthTokenUseCase,
    private val githubGetUserInfoUseCase: GithubGetUserInfoUseCase,
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun login(requestCode: String) = callbackFlow {
        githubRequestAouthTokenUseCase(requestCode).collect { githubAouthResult ->
            githubAouthResult.doWhen(
                onSuccess = { githubAouth ->
                    var githubData = GithubData(aouthToken = githubAouth.token)
                    githubGetUserInfoUseCase(githubData.aouthToken).collect { userInfoResult ->
                        userInfoResult.doWhen(
                            onSuccess = { userInfo ->
                                githubData = githubData.copy(
                                    userName = userInfo.userName,
                                    profileImageUrl = userInfo.profileImageUrl
                                )
                                Storage.write(GithubConfig.DataPath, githubData.toJsonString())
                                trySend(RequestResult.Success(githubData))
                            },
                            onFail = { exception ->
                                trySend(RequestResult.Fail(CoreException(exception.message)))
                            }
                        )
                    }
                },
                onFail = { exception ->
                    trySend(RequestResult.Fail(CoreException(exception.message)))
                }
            )
        }

        awaitClose { close() }
    }
}
