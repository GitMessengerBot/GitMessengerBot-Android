/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Ts2JsRepoImpl.kt] created by Ji Sungbin on 21. 7. 10. 오전 7:37.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.script.ts2js

import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Retrofit
import retrofit2.await

class Ts2JsRepoImpl @Inject constructor(
    private val retrofit: Retrofit
) : Ts2JsRepo {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun convert(js: String) = callbackFlow {
        try {
            trySend(
                Ts2JsResult.Success(
                    retrofit.create(Ts2JsService::class.java).convert(js).await()
                )
            )
        } catch (exception: Exception) {
            trySend(Ts2JsResult.Error(exception))
        }
        awaitClose { close() }
    }
}
