/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [MainActivity.kt] created by Ji Sungbin on 21. 5. 31. 오후 11:12.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.activity.debug.Debug
import io.github.jisungbin.gitmessengerbot.activity.main.composable.CustomWidthBottomNavigationItem
import io.github.jisungbin.gitmessengerbot.activity.main.dashboard.ScriptContent
import io.github.jisungbin.gitmessengerbot.activity.main.setting.Setting
import io.github.jisungbin.gitmessengerbot.common.constant.ScriptConstant
import io.github.jisungbin.gitmessengerbot.common.extension.doWhen
import io.github.jisungbin.gitmessengerbot.common.extension.toast
import io.github.jisungbin.gitmessengerbot.common.script.ScriptLang
import io.github.jisungbin.gitmessengerbot.theme.MaterialTheme
import io.github.jisungbin.gitmessengerbot.theme.SystemUiController
import io.github.jisungbin.gitmessengerbot.theme.colors
import io.github.jisungbin.gitmessengerbot.util.extension.composableActivityViewModel
import io.github.sungbin.gitmessengerbot.core.bot.Bot
import io.github.sungbin.gitmessengerbot.core.bot.script.ScriptItem
import io.github.sungbin.gitmessengerbot.core.service.BackgroundService
import io.github.sungbin.gitmessengerbot.core.setting.AppConfig
import kotlinx.coroutines.flow.collect

class MainActivity : ComponentActivity() {

    private var onBackPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                AppConfig.app.collect { app ->
                    val botCoreService = Intent(this@MainActivity, BackgroundService::class.java)
                    if (app.power) {
                        startService(botCoreService)
                    } else {
                        stopService(botCoreService)
                    }
                }
            }
        }

        SystemUiController(window).run {
            setStatusBarColor(colors.primary)
            setNavigationBarColor(Color.White)
        }

        lifecycleScope.launchWhenCreated {
            if (AppConfig.evalUsable) {
                Bot.compileScript(
                    context = applicationContext,
                    script = ScriptItem(
                        id = ScriptConstant.EvalId,
                        name = "",
                        lang = ScriptLang.JavaScript,
                        power = false,
                        compiled = false,
                        lastRun = ""
                    )
                ).doWhen(
                    onSuccess = {
                        toast(getString(R.string.activity_main_toast_eval_loaded))
                    },
                    onFailure = { exception ->
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

        setContent {
            MaterialTheme {
                Content()
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
    @Composable
    private fun Content() {
        val navController = rememberNavController()
        val vm: MainViewModel = composableActivityViewModel()
        val fabAction by vm.fabAction.collectAsState()
        val dashboardState by vm.dashboardState.collectAsState()

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { fabAction() }) {
                    Crossfade(dashboardState) {
                        Icon(
                            painter = painterResource(
                                when (it) {
                                    Tab.Script -> R.drawable.ic_round_add_24
                                    Tab.Debug -> R.drawable.ic_round_trash_24
                                    Tab.Setting -> R.drawable.ic_round_save_24
                                    else -> R.drawable.ic_round_add_24 // TODO
                                }
                            ),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            },
            isFloatingActionButtonDocked = true,
            floatingActionButtonPosition = FabPosition.Center,
            bottomBar = {
                BottomNavigation(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    backgroundColor = Color.White
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    val tabs = listOf(
                        Tab.Script,
                        Tab.Debug,
                        Tab.Empty,
                        Tab.Empty,
                        Tab.Kaven,
                        Tab.Setting
                    )
                    tabs.forEach { tab ->
                        if (tab == Tab.Empty) {
                            CustomWidthBottomNavigationItem(
                                width = 35.dp,
                                selected = false,
                                onClick = {},
                                icon = {},
                                enabled = false,
                            )
                        } else {
                            BottomNavigationItem(
                                icon = {
                                    Icon(
                                        painter = painterResource(tab.iconRes),
                                        contentDescription = null
                                    )
                                },
                                selectedContentColor = colors.primary,
                                unselectedContentColor = Color.LightGray,
                                alwaysShowLabel = false,
                                selected = currentDestination?.hierarchy?.any { it.route == tab.route } == true,
                                onClick = { // TODO: 백스택 뒤로가기 구현
                                    vm.updateDashboardState(tab)
                                    navController.navigate(tab.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Tab.Script.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Tab.Script.route) {
                    ScriptContent()
                }
                composable(Tab.Debug.route) {
                    Debug()
                }
                composable(Tab.Kaven.route) {
                    Text(text = stringResource(id = R.string.app_name))
                }
                composable(Tab.Setting.route) {
                    Setting()
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
