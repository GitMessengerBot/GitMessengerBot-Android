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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.activity.main.MainActivity
import me.sungbin.gitmessengerbot.activity.setup.SetupActivity
import me.sungbin.gitmessengerbot.theme.MaterialTheme
import me.sungbin.gitmessengerbot.theme.SystemUiController
import me.sungbin.gitmessengerbot.theme.colors
import me.sungbin.gitmessengerbot.util.App
import me.sungbin.gitmessengerbot.util.extension.doDelay
import me.sungbin.gitmessengerbot.util.extension.toast

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setSystemBarsColor(colors.primary)
        toast(applicationContext, "Build: ${App.BuildTime}")

        setContent {
            MaterialTheme {
                Splash()
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

    @Composable
    private fun Splash() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.primary)
                .padding(30.dp)
        ) {
            val copyright = createRef()

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
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(copyright) {
                        bottom.linkTo(parent.bottom)
                    },
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
