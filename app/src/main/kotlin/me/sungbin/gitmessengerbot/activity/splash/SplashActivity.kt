/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [SplashActivity.kt] created by Ji Sungbin on 21. 5. 21. 오후 4:39.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.activity.main.MainActivity
import me.sungbin.gitmessengerbot.activity.setup.SetupActivity
import me.sungbin.gitmessengerbot.repo.github.model.GithubData
import me.sungbin.gitmessengerbot.theme.MaterialTheme
import me.sungbin.gitmessengerbot.theme.SystemUiController
import me.sungbin.gitmessengerbot.theme.colors
import me.sungbin.gitmessengerbot.util.App
import me.sungbin.gitmessengerbot.util.PathManager
import me.sungbin.gitmessengerbot.util.Storage
import me.sungbin.gitmessengerbot.util.extension.doDelay
import me.sungbin.gitmessengerbot.util.extension.toModel
import me.sungbin.gitmessengerbot.util.extension.toast
import me.sungbin.gitmessengerbot.viewmodel.MainViewModel

class SplashActivity : ComponentActivity() {

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setSystemBarsColor(colors.primary)
        toast(applicationContext, "Build: ${App.Build}")

        setContent {
            MaterialTheme {
                Splash()
            }
        }

        if (App.isSetupDone(applicationContext)) {
            MainViewModel.instance.githubData =
                Storage.read(applicationContext, PathManager.Storage.GithubData, "")!!
                    .toModel(GithubData::class.java)
        }

        doDelay(2000) {
            finish()
            startActivity(
                Intent(
                    this,
                    if (App.isSetupDone(applicationContext)) MainActivity::class.java else SetupActivity::class.java
                )
            )
        }
    }

    @Preview
    @Composable
    private fun Splash() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.primary)
                .padding(30.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_round_logo_512),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(150.dp)
                )
                Text(
                    text = with(AnnotatedString.Builder(stringResource(R.string.app_name))) {
                        addStyle(SpanStyle(fontWeight = FontWeight.Bold), 3, 12)
                        toAnnotatedString()
                    },
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 30.dp)
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.copyright),
                    color = Color.White,
                    fontSize = 10.sp
                )
            }
        }
    }
}
