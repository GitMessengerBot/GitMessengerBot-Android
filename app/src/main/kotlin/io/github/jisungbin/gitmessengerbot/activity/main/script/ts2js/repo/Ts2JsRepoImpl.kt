/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Ts2JsRepoImpl.kt] created by Ji Sungbin on 21. 7. 10. 오전 7:37.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.main.script.ts2js.repo

import io.github.jisungbin.gitmessengerbot.activity.main.script.ts2js.Ts2JsResponse
import io.github.jisungbin.gitmessengerbot.repo.Result
import io.github.jisungbin.gitmessengerbot.util.Json
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import org.jsoup.Connection

class Ts2JsRepoImpl @Inject constructor(
    private val jsoup: Connection
) : Ts2JsRepo {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun convert(js: String) = callbackFlow {
        runCatching {
            try {
                Thread {
                    trySend(
                        Result.Success(
                            Json.toModel(
                                jsoup.requestBody(js).post().text(),
                                Ts2JsResponse::class
                            )
                        )
                    )
                }.start()
            } catch (exception: Exception) {
                trySend(Result.Fail(exception))
            }
        }

        awaitClose { close() }
    }
}
