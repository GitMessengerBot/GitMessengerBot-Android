/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [BaseUseCase.kt] created by Ji Sungbin on 21. 8. 28. 오후 11:28
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain.github

import kotlinx.coroutines.flow.Flow

interface BaseUseCase<in Parameter, out ResultType> {
    suspend operator fun invoke(
        parameter: Parameter,
    ): Flow<GithubResult<ResultType>>
}

interface BaseUseCase2<in Parameter, in Parameter2, out ResultType> {
    suspend operator fun invoke(
        parameter: Parameter,
        parameter2: Parameter2,
    ): Flow<GithubResult<ResultType>>
}

interface BaseUseCase3<in Parameter, in Parameter2, in Parameter3, out ResultType> {
    suspend operator fun invoke(
        parameter: Parameter,
        parameter2: Parameter2,
        parameter3: Parameter3,
    ): Flow<GithubResult<ResultType>>
}
