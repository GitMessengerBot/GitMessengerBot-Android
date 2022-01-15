/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Ts2JsRepo.kt] created by Ji Sungbin on 21. 7. 10. 오전 7:36.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.bot.script.ts2js.repo

import io.github.sungbin.gitmessengerbot.core.bot.script.ts2js.Ts2JsResponse

internal interface Ts2JsRepo {
    suspend fun convert(js: String): Result<Ts2JsResponse>
}
