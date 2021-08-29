/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ScriptCompiler.kt] created by Ji Sungbin on 21. 8. 28. 오후 11:59
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.bot.script.compiler.repo

import android.content.Context
import io.github.jisungbin.gitmessengerbot.util.Nothing
import io.github.sungbin.gitmessengerbot.core.CoreResult
import io.github.sungbin.gitmessengerbot.core.bot.script.ScriptItem
import kotlinx.coroutines.flow.Flow

internal interface ScriptCompiler {
    fun process(context: Context, script: ScriptItem): Flow<CoreResult<Nothing>>
}
