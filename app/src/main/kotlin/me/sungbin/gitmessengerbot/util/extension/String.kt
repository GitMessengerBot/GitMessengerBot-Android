/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [String.kt] created by Ji Sungbin on 21. 6. 14. 오후 9:22.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.util.extension

import com.google.gson.Gson

inline fun <reified T> String.toModel(clazz: Class<T>) = Gson().fromJson(this, clazz)!!
