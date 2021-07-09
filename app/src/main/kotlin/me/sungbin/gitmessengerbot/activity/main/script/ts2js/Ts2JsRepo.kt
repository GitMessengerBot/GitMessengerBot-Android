/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Ts2JsRepo.kt] created by Ji Sungbin on 21. 7. 10. 오전 7:36.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.script.ts2js

import kotlinx.coroutines.flow.Flow

interface Ts2JsRepo {
    fun convert(js: String): Flow<Ts2JsResult>
}
