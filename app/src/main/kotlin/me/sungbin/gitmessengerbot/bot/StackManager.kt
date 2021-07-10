/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [StackManager.kt] created by Ji Sungbin on 21. 7. 10. 오전 11:25.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [StackManager.kt] created by Ji Sungbin on 21. 7. 10. 오전 11:25.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [StackManager.kt] created by Ji Sungbin on 21. 7. 10. 오전 11:16.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.bot

import android.app.Notification
import com.eclipsesource.v8.V8

object StackManager {
    val sessions = HashMap<String, Notification.Action>()
    val v8 = HashMap<Int, V8>()
}
