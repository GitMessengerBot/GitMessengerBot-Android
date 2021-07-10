/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [File.kt] created by Ji Sungbin on 21. 7. 10. 오전 11:33.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.bot.api

import me.sungbin.gitmessengerbot.util.Storage

class File {
    fun save(path: String, content: String) = Storage.write(path, content)
    fun read(path: String, _null: String? = null) = Storage.read(path, _null)
    fun append(path: String, content: String, appendPrefix: String) =
        Storage.append(path, content, appendPrefix)
}
