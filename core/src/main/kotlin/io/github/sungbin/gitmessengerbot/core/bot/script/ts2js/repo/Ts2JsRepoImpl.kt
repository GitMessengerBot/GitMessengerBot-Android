/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Ts2JsRepoImpl.kt] created by Ji Sungbin on 21. 7. 10. 오전 7:37.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.bot.script.ts2js.repo

import io.github.jisungbin.gitmessengerbot.util.extension.ioThread
import io.github.jisungbin.gitmessengerbot.util.extension.toModel
import io.github.jisungbin.gitmessengerbot.util.repo.RequestResult
import io.github.sungbin.gitmessengerbot.core.bot.script.ts2js.Ts2JsResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import org.jsoup.Connection
import javax.inject.Inject

class Ts2JsRepoImpl @Inject constructor(
    private val jsoup: Connection,
) : Ts2JsRepo {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun convert(js: String) = callbackFlow {
        try {
            ioThread {
                val result = jsoup.requestBody(js).post().text().toModel(Ts2JsResponse::class)
                trySend(RequestResult.Success(result))
            }
        } catch (exception: Exception) {
            trySend(RequestResult.Fail(exception))
        }

        awaitClose { close() }
    }
}
