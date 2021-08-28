/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Util.kt] created by Ji Sungbin on 21. 8. 28. 오후 10:14
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.util.core

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import io.github.jisungbin.gitmessengerbot.util.R
import io.github.jisungbin.gitmessengerbot.util.extension.toast

object Util {
    fun copy(context: Context, text: String, showToast: Boolean = true) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("label", text))
        if (showToast) {
            toast(
                context,
                context.getString(R.string.util_toast_copied_clipboard)
            )
        }
    }
}
