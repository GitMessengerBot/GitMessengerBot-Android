/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [addApi.kt] created by Ji Sungbin on 21. 7. 17. 오전 3:46.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.script.compiler.util

import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object

@Suppress("DEPRECATION")
fun V8.addApi(
    apiName: String,
    apiClass: Any,
    methodNames: List<String>,
    argumentsList: List<List<Class<*>>>
) {
    val api = V8Object(this)
    this.add(apiName, api)

    for ((index, methodName) in methodNames.withIndex()) {
        api.registerJavaMethod(
            apiClass,
            methodName,
            methodName,
            argumentsList[index].toTypedArray()
        )
    }

    api.release()
}
