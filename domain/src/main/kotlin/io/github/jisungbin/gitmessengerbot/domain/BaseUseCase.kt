/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [BaseUseCase.kt] created by Ji Sungbin on 21. 8. 13. 오후 7:41.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.domain

interface BaseUseCase<in Parameter, out Result> {
    suspend operator fun invoke(
        parameter: Parameter
    ): Result
}

interface BaseUseCase2<in Parameter, in Parameter2, out Result> {
    suspend operator fun invoke(
        parameter: Parameter,
        parameter2: Parameter2
    ): Result
}

interface BaseUseCase3<in Parameter, in Parameter2, in Parameter3, out Result> {
    suspend operator fun invoke(
        parameter: Parameter,
        parameter2: Parameter2,
        parameter3: Parameter3
    ): Result
}

interface BaseUseCase4<in Parameter, in Parameter2, in Parameter3, in Parameter4, out Result> {
    suspend operator fun invoke(
        parameter: Parameter,
        parameter2: Parameter2,
        parameter3: Parameter3,
        parameter4: Parameter4,
    ): Result
}

interface BaseUseCase5<in Parameter, in Parameter2, in Parameter3, in Parameter4, in Parameter5, out Result> {
    suspend operator fun invoke(
        parameter: Parameter,
        parameter2: Parameter2,
        parameter3: Parameter3,
        parameter4: Parameter4,
        parameter5: Parameter5
    ): Result
}

interface BaseUseCaseVararg<in Parameter, out Result> {
    suspend operator fun invoke(
        vararg parameter: Parameter
    ): Result
}
