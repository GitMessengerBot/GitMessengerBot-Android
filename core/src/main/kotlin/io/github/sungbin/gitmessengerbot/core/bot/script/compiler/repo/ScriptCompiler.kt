/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Compiler.kt] created by Ji Sungbin on 21. 7. 12. 오후 9:57.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.bot.script.compiler.repo

import android.content.Context
import io.github.jisungbin.gitmessengerbot.util.repo.Nothing
import io.github.jisungbin.gitmessengerbot.util.repo.RequestResult
import io.github.sungbin.gitmessengerbot.core.bot.script.ScriptItem
import kotlinx.coroutines.flow.Flow

interface ScriptCompiler {
    fun process(context: Context, script: ScriptItem): Flow<RequestResult<Nothing>>
}
