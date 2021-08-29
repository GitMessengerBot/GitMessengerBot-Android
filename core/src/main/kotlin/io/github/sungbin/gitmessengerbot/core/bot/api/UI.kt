/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [UI.kt] created by Ji Sungbin on 21. 7. 10. 오전 11:35.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.bot.api

import android.content.Context

// TODO: activity 메모리 누수 해결
internal class UI(private val context: Context) { // TODO
    fun toast(message: String) {
        io.github.jisungbin.gitmessengerbot.util.extension.toast(context, message)
    }
}
