/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Compiler.kt] created by Ji Sungbin on 21. 7. 12. 오후 9:57.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.script.compiler.repo

import android.content.Context
import kotlinx.coroutines.flow.Flow
import me.sungbin.gitmessengerbot.activity.main.script.compiler.CompileResult
import me.sungbin.gitmessengerbot.activity.main.script.ScriptItem

interface ScriptCompiler {
    fun process(context: Context, script: ScriptItem): Flow<CompileResult>
}
