/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [extension.kt] created by Ji Sungbin on 21. 7. 13. 오전 2:20.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.editor.git.util

import android.util.Base64

fun String.toBase64() = Base64.encodeToString(toByteArray(), Base64.NO_WRAP)!!