/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [SettingViewModel.kt] created by Ji Sungbin on 21. 7. 8. 오후 9:02.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [SettingViewModel.kt] created by Ji Sungbin on 21. 7. 8. 오후 9:02.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [SettingViewModel.kt] created by Ji Sungbin on 21. 6. 19. 오후 11:46.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.setting

import androidx.lifecycle.ViewModel

class SettingViewModel private constructor() : ViewModel() {

    fun save() {
    }

    companion object {
        val instance by lazy { SettingViewModel() }
    }
}
