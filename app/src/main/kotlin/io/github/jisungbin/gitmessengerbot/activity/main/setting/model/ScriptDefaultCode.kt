/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ScriptDefaultCode.kt] created by Ji Sungbin on 21. 7. 24. 오후 11:13.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.main.setting.model

import io.github.jisungbin.gitmessengerbot.bot.Bot

data class ScriptDefaultCode(
    var js: String = "const ${Bot.app.value.scriptResponseFunctioName} = (room, message, sender, isGroupChat, profileImageBase64, isDebugMode) => {\n\t\tconst reply = (message) => {\n\t\t\t\tBot.reply(room, message, isDebugMode);\n\t\t}\n}",
    var ts: String = "const ${Bot.app.value.scriptResponseFunctioName} = (room: string, message: string, sender: string, isGroupChat: boolean, profileImageBase64: string, isDebugMode: Boolean) => {\n\t\tconst reply = (message: String) => {\n\t\t\t\tBot.reply(room, message, isDebugMode);\n\t\t}\n}",
    var python: String = "def ${Bot.app.value.scriptResponseFunctioName}(self, room, message, sender, isGroupChat, profileImageBase64):\n\t\t\t\t",
    var sim: String = "sim"
)
