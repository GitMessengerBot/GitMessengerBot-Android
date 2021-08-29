/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [SetupViewModel.kt] created by Ji Sungbin on 21. 8. 29. 오후 7:22
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.setup

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.activity.home.main.MainActivity
import io.github.jisungbin.gitmessengerbot.activity.setup.model.GithubData
import io.github.jisungbin.gitmessengerbot.domain.github.doWhen
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubGetUserInfoUseCase
import io.github.jisungbin.gitmessengerbot.domain.github.usecase.GithubRequestAouthTokenUseCase
import io.github.jisungbin.gitmessengerbot.util.Nothing
import io.github.jisungbin.gitmessengerbot.util.RequestResult
import io.github.jisungbin.gitmessengerbot.util.config.GithubConfig
import io.github.jisungbin.gitmessengerbot.util.exception.CoreException
import io.github.jisungbin.gitmessengerbot.util.extension.toJsonString
import io.github.jisungbin.gitmessengerbot.util.extension.toast
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(
    private val githubGetUserInfoUseCase: GithubGetUserInfoUseCase,
    private val githubRequestAouthTokenUseCase: GithubRequestAouthTokenUseCase,
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun login(requestCode: String, activity: Activity) = callbackFlow<RequestResult<Nothing>> {
        githubRequestAouthTokenUseCase.invoke(requestCode).collect { aouthTokenResult ->
            aouthTokenResult.doWhen(
                onSuccess = { aouth ->
                    var githubData = GithubData(token = aouth.accessToken)
                    githubGetUserInfoUseCase.invoke(githubData.token).collect { userInfoResult ->
                        userInfoResult.doWhen(
                            onSuccess = { user ->
                                githubData = githubData.copy(
                                    userName = user.login,
                                    profileImageUrl = user.avatarUrl
                                )

                                Storage.write(
                                    GithubConfig.DataPath,
                                    githubData.toJsonString()
                                )

                                activity.finish()
                                startActivity(
                                    Intent(
                                        activity,
                                        MainActivity::class.java
                                    )
                                )

                                toast(
                                    activity,
                                    activity.getString(
                                        R.string.vm_setup_toast_welcome_start,
                                        user.login
                                    )
                                )

                                trySned(RequestResult.Success(Nothing()))
                            },
                            onFail = { exception ->
                                trySend(
                                    RequestResult.Fail(
                                        CoreException(
                                            activity.getString(
                                                R.string.vm_setup_exception_github_connect,
                                                exception.message
                                            )
                                        )
                                    )
                                )
                            }
                        )
                    }
                },
                onFail = { exception ->
                    trySend(
                        RequestResult.Fail(
                            CoreException(
                                activity.getString(
                                    R.string.vm_setup_exception_github_authorize,
                                    exception.message
                                )
                            )
                        )
                    )
                }
            )
        }

        awaitClose { close() }
    }
}
