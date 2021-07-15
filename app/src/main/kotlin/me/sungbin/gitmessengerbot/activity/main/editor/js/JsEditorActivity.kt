/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [EditorActivity.kt] created by Ji Sungbin on 21. 7. 10. 오전 6:01.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main.editor.js

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import me.sungbin.gitmessengerbot.activity.main.editor.git.repo.GitRepo
import me.sungbin.gitmessengerbot.bot.Bot
import me.sungbin.gitmessengerbot.theme.MaterialTheme
import me.sungbin.gitmessengerbot.theme.SystemUiController
import me.sungbin.gitmessengerbot.theme.colors
import me.sungbin.gitmessengerbot.util.config.StringConfig

@AndroidEntryPoint
class JsEditorActivity : ComponentActivity() {

    @Inject
    lateinit var gitRepo: GitRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).run {
            setStatusBarColor(colors.primary)
            setNavigationBarColor(Color.White)
        }

        val scriptId = intent.getIntExtra(StringConfig.IntentScriptId, -1)

        setContent {
            MaterialTheme {
                Editor(
                    script = Bot.getScriptById(scriptId),
                    gitRepo = gitRepo
                )
            }
        }
    }
}
