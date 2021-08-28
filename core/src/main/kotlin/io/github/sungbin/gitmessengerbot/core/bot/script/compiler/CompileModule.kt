/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [CompileModule.kt] created by Ji Sungbin on 21. 7. 12. 오후 9:56.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.bot.script.compiler

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.sungbin.gitmessengerbot.core.bot.script.compiler.repo.ScriptCompiler
import io.github.sungbin.gitmessengerbot.core.bot.script.compiler.repo.ScriptCompilerImpl
import io.github.sungbin.gitmessengerbot.core.bot.script.ts2js.repo.Ts2JsRepo
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CompileModule {
    @Provides
    @Singleton
    fun provideRepo(ts2JsRepo: Ts2JsRepo): ScriptCompiler = ScriptCompilerImpl(ts2JsRepo)
}
