/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [SetupSideEffect.kt] created by Ji Sungbin on 21. 9. 18. 오후 9:14
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.setup

import io.github.jisungbin.gitmessengerbot.domain.github.model.user.GithubData

sealed class MviSetupSideEffect {
    data class Error(val exception: Exception) : MviSetupSideEffect()
    data class SaveData(val data: GithubData) : MviSetupSideEffect()
}
