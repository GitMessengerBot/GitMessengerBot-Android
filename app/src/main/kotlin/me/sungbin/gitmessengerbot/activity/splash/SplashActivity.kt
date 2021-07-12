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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import java.util.Calendar
import me.sungbin.gitmessengerbot.BuildConfig
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.activity.main.MainActivity
import me.sungbin.gitmessengerbot.activity.setup.SetupActivity
import me.sungbin.gitmessengerbot.theme.MaterialTheme
import me.sungbin.gitmessengerbot.theme.SystemUiController
import me.sungbin.gitmessengerbot.theme.colors
import me.sungbin.gitmessengerbot.util.Storage
import me.sungbin.gitmessengerbot.util.config.PathConfig
import me.sungbin.gitmessengerbot.util.extension.doDelay
import me.sungbin.gitmessengerbot.util.extension.toast

class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setSystemBarsColor(colors.primary)
        setContent {
            MaterialTheme {
                Splash()
            }
        }

        val isSetupDone = Storage.read(PathConfig.GithubData, null) != null
        val builtDate = Calendar.getInstance().apply { timeInMillis = BuildConfig.TIMESTAMP }
        val builtTime = "${builtDate.get(Calendar.MINUTE)}m ${builtDate.get(Calendar.SECOND)}s"
        toast(this, "Built at: $builtTime")

        doDelay(2000) {
            finish()
            startActivity(
                Intent(
                    this,
                    if (isSetupDone || !BuildConfig.DEBUG) MainActivity::class.java else SetupActivity::class.java
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
            val (content, footer) = createRefs()

            Column(
                modifier = Modifier.constrainAs(content) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
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
                        addStyle(SpanStyle(fontWeight = FontWeight.Bold), 3, 12) // Messenger
                        toAnnotatedString()
                    },
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 30.dp)
                )
            }
            Text(
                text = stringResource(R.string.copyright),
                color = Color.White,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(footer) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }
            )
        }
    }
}
