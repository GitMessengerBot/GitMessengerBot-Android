/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Ts2JsRepoImpl.kt] created by Ji Sungbin on 21. 7. 10. 오전 7:37.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.bot.script.ts2js.repo

import io.github.jisungbin.gitmessengerbot.common.extension.ioThread
import io.github.jisungbin.gitmessengerbot.common.extension.toModel
import io.github.sungbin.gitmessengerbot.core.bot.script.ts2js.Ts2JsResponse
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine
import org.jsoup.Connection

internal class Ts2JsRepoImpl(private val jsoup: Connection) : Ts2JsRepo {
    override suspend fun convert(js: String): Result<Ts2JsResponse> =
        suspendCancellableCoroutine { continuation ->
            try {
                ioThread {
                    val ts2js: Ts2JsResponse = jsoup.requestBody(js).post().text().toModel()
                    continuation.resume(Result.success(ts2js))
                }
            } catch (exception: Exception) {
                continuation.resume(Result.failure(exception))
            }
        }
}
