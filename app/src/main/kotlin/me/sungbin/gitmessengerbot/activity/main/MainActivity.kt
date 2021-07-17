/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [MainActivity.kt] created by Ji Sungbin on 21. 5. 31. 오후 11:12.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.main

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.activity.main.script.ScriptAddContent
import me.sungbin.gitmessengerbot.activity.main.script.ScriptContent
import me.sungbin.gitmessengerbot.activity.main.script.compiler.repo.ScriptCompiler
import me.sungbin.gitmessengerbot.bot.Bot
import me.sungbin.gitmessengerbot.service.BackgroundService
import me.sungbin.gitmessengerbot.theme.MaterialTheme
import me.sungbin.gitmessengerbot.theme.SystemUiController
import me.sungbin.gitmessengerbot.theme.colors
import me.sungbin.gitmessengerbot.ui.fancybottombar.FancyBottomBar
import me.sungbin.gitmessengerbot.ui.fancybottombar.FancyColors
import me.sungbin.gitmessengerbot.ui.fancybottombar.FancyItem

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var scriptCompiler: ScriptCompiler

    private var tab by mutableStateOf(Tab.Script)
    private lateinit var onBackPressedAction: () -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Bot.app.value.power) {
            startService(Intent(this, BackgroundService::class.java))
        }

        SystemUiController(window).run {
            setStatusBarColor(colors.primary)
            setNavigationBarColor(Color.White)
        }

        setContent {
            MaterialTheme {
                Main()
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun Main() {
        val coroutineScope = rememberCoroutineScope()
        val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
        )
        onBackPressedAction = {
            coroutineScope.launch {
                if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                    bottomSheetScaffoldState.bottomSheetState.collapse()
                } else {
                    finish()
                }
            }
        }
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            sheetContent = { ScriptAddContent(bottomSheetScaffoldState) },
            sheetElevation = 0.dp,
            sheetPeekHeight = 0.dp,
            modifier = Modifier.fillMaxSize()
        ) {
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
                            compiler = scriptCompiler
                        )
                        else -> Text(text = "TODO")
                    }
                }
                Footer(bottomSheetScaffoldState = bottomSheetScaffoldState)
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
    @Composable
    private fun Footer(bottomSheetScaffoldState: BottomSheetScaffoldState) {
        val coroutineScope = rememberCoroutineScope()
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
                            coroutineScope.launch {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
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
        if (::onBackPressedAction.isInitialized) {
            onBackPressedAction()
        } else {
            super.onBackPressed()
        }
    }
}
