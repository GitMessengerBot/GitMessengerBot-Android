/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Json.kt] created by Ji Sungbin on 21. 7. 10. 오전 12:33.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.util

import com.google.gson.Gson
import kotlin.reflect.KClass

object Json {
    inline fun <reified T : Any> toModel(json: String, clazz: KClass<T>) =
        Gson().fromJson(json, clazz.java)!!

    fun toString(src: Any) = Gson().toJson(src)!!
}
