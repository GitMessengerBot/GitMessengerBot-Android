/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [CompileModule.kt] created by Ji Sungbin on 21. 7. 12. 오후 9:56.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.script.compiler

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import me.sungbin.gitmessengerbot.activity.main.script.compiler.repo.ScriptCompiler
import me.sungbin.gitmessengerbot.activity.main.script.compiler.repo.ScriptCompilerImpl
import me.sungbin.gitmessengerbot.activity.main.script.ts2js.repo.Ts2JsRepo

@Module
@InstallIn(SingletonComponent::class)
object CompileModule {

    @Provides
    @Singleton
    fun provideRepo(ts2JsRepo: Ts2JsRepo): ScriptCompiler = ScriptCompilerImpl(ts2JsRepo)
}
