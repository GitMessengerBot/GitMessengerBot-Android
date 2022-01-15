/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [json.kt] created by Ji Sungbin on 21. 8. 30. 오후 5:05
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.common.extension

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.jisungbin.gitmessengerbot.common.exception.CommonException

@PublishedApi
internal val mapper by lazy {
    ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerKotlinModule()
}

inline fun <reified T> String.toModel(): T = mapper.readValue(this, T::class.java)
    ?: throw CommonException("문자열을 json 모델로 바꾸는데 오류가 발생했어요.\n\n($this)")

fun Any.toJsonString() = mapper.writeValueAsString(this)
    ?: throw CommonException("json 모델을 문자열로 바꾸는데 오류가 발생했어요.\n\n($this)")
