/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [BeautifyRepo.kt] created by Ji Sungbin on 21. 7. 13. 오전 2:34.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.editor.beautify.repo

import kotlinx.coroutines.flow.Flow

interface BeautifyRepo {
    fun pretty(code: String): Flow<BeautifyResult>
    fun minify(code: String): Flow<BeautifyResult>
}