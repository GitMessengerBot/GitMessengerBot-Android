/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [StackManager.kt] created by Ji Sungbin on 21. 7. 10. 오전 11:16.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.bot

import androidx.core.app.NotificationCompat
import com.eclipsesource.v8.V8

internal object StackManager {
    val sessions: HashMap<String, NotificationCompat.Action> = hashMapOf()
    val v8: HashMap<Int, V8> = hashMapOf()
}
