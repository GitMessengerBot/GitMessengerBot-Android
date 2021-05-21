/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.theme.BindView
import me.sungbin.gitmessengerbot.theme.SystemUiController
import me.sungbin.gitmessengerbot.theme.colors
import me.sungbin.gitmessengerbot.theme.defaultFontFamily
import me.sungbin.gitmessengerbot.util.App
import me.sungbin.gitmessengerbot.util.doDelay

@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setSystemBarsColor(colors.primary)

        setContent {
            BindView {
                SplashView()
            }
        }

        doDelay(2000) {
            finish()
            startActivity(
                Intent(
                    this,
                    if (App.isSetupDone()) MainActivity::class.java else SetupActivity::class.java
                )
            )
        }
    }

    @Preview
    @Composable
    private fun SplashView() {
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
                Image(
                    painter = painterResource(R.drawable.ic_baseline_logo_512),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier.size(150.dp)
                )
                Text(
                    text = with(AnnotatedString.Builder(stringResource(R.string.app_name))) {
                        addStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontFamily = defaultFontFamily
                            ),
                            3, 12
                        )
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
