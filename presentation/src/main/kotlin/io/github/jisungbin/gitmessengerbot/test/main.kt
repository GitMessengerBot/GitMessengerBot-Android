/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [main.kt] created by Ji Sungbin on 21. 9. 16. 오후 12:49
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.test

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.runBlocking

lateinit var flow: Flow<String>

fun main() = runBlocking {
    println("Start")
    flowTest()
    flow.shareIn(this, SharingStarted.WhileSubscribed()).collect {
        println(it)
        cancel()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun flowTest() {
    flow = callbackFlow {
        trySend("Done.")
        awaitClose { println("close") }
    }
}
