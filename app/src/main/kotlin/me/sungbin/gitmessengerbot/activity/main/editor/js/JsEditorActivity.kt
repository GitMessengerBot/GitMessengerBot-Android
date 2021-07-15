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
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch
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

    private lateinit var onBackPressedAction: () -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).run {
            setStatusBarColor(colors.primary)
            setNavigationBarColor(Color.White)
        }

        val scriptId = intent.getIntExtra(StringConfig.IntentScriptId, -1)

        setContent {
            MaterialTheme {
                val scaffoldState = rememberScaffoldState()
                val coroutineScope = rememberCoroutineScope()

                onBackPressedAction = {
                    if (scaffoldState.drawerState.isOpen) {
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                        }
                    } else {
                        super.onBackPressed()
                    }
                }

                Editor(
                    script = Bot.getScriptById(scriptId),
                    gitRepo = gitRepo,
                    scaffoldState = scaffoldState
                )
            }
        }
    }

    override fun onBackPressed() {
        if (::onBackPressedAction.isInitialized) {
            onBackPressedAction()
        } else {
            super.onBackPressed()
        }
    }
}
