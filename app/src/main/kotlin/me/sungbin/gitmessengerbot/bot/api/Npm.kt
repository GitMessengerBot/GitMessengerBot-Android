/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Npm.kt] created by Ji Sungbin on 21. 7. 17. 오전 3:12.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.bot.api

import com.eclipsesource.v8.NodeJS
import java.io.File
import me.sungbin.gitmessengerbot.activity.main.script.compiler.util.addApi
import me.sungbin.gitmessengerbot.bot.StackManager
import me.sungbin.gitmessengerbot.util.config.StringConfig

class Npm {
    fun execute(moduleName: String, code: String) {
        if (StackManager.npm[moduleName] == null) {
            val module = NodeJS.createNodeJS().require(File(StringConfig.ModulePath(moduleName)))!!
            module.runtime.addApi(
                apiName = "Log",
                apiClass = Log(),
                methodNames = listOf("print"),
                argumentsList = listOf(listOf(Any::class.java))
            )
            StackManager.npm[moduleName] = module
        }
        val module = StackManager.npm[moduleName]!!
        module.runtime.executeScript(code)
        module.runtime.locker.release()
    }
}
