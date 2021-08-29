/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ScriptDefaultCode.kt] created by Ji Sungbin on 21. 8. 29. 오후 9:14
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.sungbin.gitmessengerbot.core.setting.model

import io.github.jisungbin.gitmessengerbot.util.config.ScriptConfig

// TODO: sim
data class ScriptDefaultCode(
    var js: String = "const ${ScriptConfig.DefaultResponseFunctionName} = (room, message, sender, isGroupChat, profileImageBase64, isDebugMode) => {\n\t\tconst reply = (message) => {\n\t\t\t\tBot.reply(room, message, isDebugMode);\n\t\t}\n}",
    var ts: String = "const ${ScriptConfig.DefaultResponseFunctionName} = (room: string, message: string, sender: string, isGroupChat: boolean, profileImageBase64: string, isDebugMode: Boolean) => {\n\t\tconst reply = (message: String) => {\n\t\t\t\tBot.reply(room, message, isDebugMode);\n\t\t}\n}",
    var py: String = "def ${ScriptConfig.DefaultResponseFunctionName}(self, room, message, sender, isGroupChat, profileImageBase64):\n\t\t\t\t",
    var sim: String = "todo",
)
