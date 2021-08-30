/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Unit.kt] created by Ji Sungbin on 21. 8. 28. 오후 2:36.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.common.extension

import android.os.Handler
import android.os.Looper

inline fun doDelay(ms: Long, crossinline action: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({ action() }, ms)
}
