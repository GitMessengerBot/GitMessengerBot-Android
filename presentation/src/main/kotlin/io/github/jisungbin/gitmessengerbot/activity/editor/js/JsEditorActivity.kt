/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [EditorActivity.kt] created by Ji Sungbin on 21. 7. 10. 오전 6:01.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.editor.js

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import dagger.hilt.android.AndroidEntryPoint
import io.github.jisungbin.gitmessengerbot.common.constant.IntentConstant
import io.github.jisungbin.gitmessengerbot.common.exception.PresentationException
import io.github.jisungbin.gitmessengerbot.theme.MaterialTheme
import io.github.jisungbin.gitmessengerbot.theme.SystemUiController
import io.github.jisungbin.gitmessengerbot.theme.colors
import io.github.sungbin.gitmessengerbot.core.bot.Bot
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JsEditorActivity : ComponentActivity() {

    private var onBackPressedAction: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).run {
            setStatusBarColor(colors.primary)
            setNavigationBarColor(Color.White)
        }

        val scriptId = intent.getIntExtra(IntentConstant.ScriptId, -1)

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

                val script = try {
                    Bot.getAllScripts().first { it.id == scriptId }
                } catch (exception: Exception) {
                    throw PresentationException("$scriptId 스크립트가 존재하지 않아요. (${exception.message})")
                }

                Editor(
                    script = script,
                    scaffoldState = scaffoldState
                )
            }
        }
    }

    override fun onBackPressed() {
        onBackPressedAction?.invoke() ?: super.onBackPressed()
    }
}
