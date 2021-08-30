/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [MainActivity.kt] created by Ji Sungbin on 21. 5. 31. 오후 11:12.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.home.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.activity.home.setting.Setting
import io.github.jisungbin.gitmessengerbot.theme.MaterialTheme
import io.github.jisungbin.gitmessengerbot.theme.SystemUiController
import io.github.jisungbin.gitmessengerbot.theme.colors
import io.github.jisungbin.gitmessengerbot.common.config.ScriptConfig
import io.github.jisungbin.gitmessengerbot.common.extension.toast
import io.github.jisungbin.gitmessengerbot.common.script.ScriptLang
import io.github.sungbin.gitmessengerbot.core.bot.Bot
import io.github.sungbin.gitmessengerbot.core.bot.debug.Debug
import io.github.sungbin.gitmessengerbot.core.bot.script.ScriptItem
import io.github.sungbin.gitmessengerbot.core.doWhen
import io.github.sungbin.gitmessengerbot.core.script.ScriptContent
import io.github.sungbin.gitmessengerbot.core.service.BackgroundService
import io.github.sungbin.gitmessengerbot.core.setting.AppConfig
import kotlinx.coroutines.flow.collect
import me.sungbin.fancybottombar.FancyBottomBar
import me.sungbin.fancybottombar.FancyColors
import me.sungbin.fancybottombar.FancyItem

class MainActivity : ComponentActivity() {

    private var onBackPressedTime = 0L
    private var tab by mutableStateOf(Tab.Script)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppConfig.app.observe(this) { app ->
            val botCoreService = Intent(this, BackgroundService::class.java)
            if (app.power) {
                startService(botCoreService)
            } else {
                stopService(botCoreService)
            }
        }

        SystemUiController(window).run {
            setStatusBarColor(colors.primary)
            setNavigationBarColor(Color.White)
        }

        lifecycleScope.launchWhenCreated {
            if (AppConfig.canUseEval) {
                Bot.compileScript(
                    applicationContext,
                    ScriptItem(
                        id = ScriptConfig.EvalId,
                        name = "",
                        lang = ScriptLang.JavaScript,
                        power = false,
                        compiled = false,
                        lastRun = ""
                    )
                ).collect { compileResult ->
                    compileResult.doWhen(
                        onSuccess = {
                            toast(getString(R.string.activity_main_toast_eval_loaded))
                        },
                        onFail = { exception ->
                            toast(
                                getString(
                                    R.string.activity_main_toast_eval_load_fail,
                                    exception.message
                                )
                            )
                        }
                    )
                }
            }
        }

        setContent {
            MaterialTheme {
                Content()
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun Content() {
        val scriptAddDialogVisible = remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.primary)
        ) {
            Crossfade(
                targetState = tab,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp)
            ) { index ->
                when (index) {
                    Tab.Script -> ScriptContent(
                        activity = this@MainActivity,
                        compiler = scriptCompiler,
                        scriptAddDialogVisible = scriptAddDialogVisible
                    )
                    Tab.Debug -> Debug(activity = this@MainActivity)
                    Tab.Setting -> Setting(this@MainActivity)
                    else -> Text(text = "TODO")
                }
            }
            Footer(scriptAddDialogVisible)
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    private fun Footer(scriptAddDialogVisible: MutableState<Boolean>) {
        val items = listOf(
            FancyItem(icon = R.drawable.ic_round_script_24, id = 0),
            FancyItem(icon = R.drawable.ic_round_debug_24, id = 1),
            FancyItem(icon = R.drawable.ic_round_github_24, id = 2),
            FancyItem(icon = R.drawable.ic_round_settings_24, id = 3)
        )

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            FancyBottomBar(
                fancyColors = FancyColors(primary = colors.primary),
                items = items
            ) { tab = id }
            AnimatedVisibility(
                visible = tab == Tab.Script,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Surface(
                    modifier = Modifier
                        .padding(bottom = 35.dp)
                        .size(50.dp)
                        .clip(CircleShape)
                        .clickable {
                            scriptAddDialogVisible.value = true
                        },
                    color = colors.primary,
                    elevation = 2.dp
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_round_add_24),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }

    override fun onBackPressed() {
        val now = System.currentTimeMillis()
        if (now - onBackPressedTime >= 3000) {
            onBackPressedTime = now
            toast(getString(R.string.activity_main_toast_confirm_finish))
        } else {
            super.onBackPressed()
        }
    }
}
