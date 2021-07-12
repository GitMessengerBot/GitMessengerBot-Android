/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [BeautifyRepoImpl.kt] created by Ji Sungbin on 21. 7. 13. 오전 2:34.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.editor.beautify.repo

import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import org.json.JSONObject
import org.jsoup.Connection

class BeautifyRepoImpl @Inject constructor(
    private val pretty: Connection,
    private val minify: Connection
) : BeautifyRepo {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun pretty(code: String) = callbackFlow {
        try {
            runCatching {
                val response = JSONObject(pretty.data("input", code).post().wholeText())["output"]
                trySend(BeautifyResult.Success(response.toString()))
            }
        } catch (exception: Exception) {
            trySend(BeautifyResult.Error(exception))
        }

        awaitClose { close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun minify(code: String) = callbackFlow {
        try {
            runCatching {
                val response = minify.data("input", code).post().wholeText()
                trySend(BeautifyResult.Success(response.toString()))
            }
        } catch (exception: Exception) {
            trySend(BeautifyResult.Error(exception))
        }

        awaitClose { close() }
    }
}