/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [json.kt] created by Ji Sungbin on 21. 8. 30. 오후 5:05
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.common.extension

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.jisungbin.gitmessengerbot.common.exception.CommonException

@PublishedApi
internal val mapper by lazy { ObjectMapper() }

inline fun <reified T> String.toModel(): T = mapper.readValue(this, T::class.java)
    ?: throw CommonException("Error occur when convert string to json-object. ($this)")

fun Any.toJsonString() = mapper.writeValueAsString(this)
    ?: throw CommonException("Error occur when convert json-object to string. ($this)")
