/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [SplashActivity.kt] created by Ji Sungbin on 21. 5. 21. 오후 4:39.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import io.github.jisungbin.gitmessengerbot.BuildConfig
import io.github.jisungbin.gitmessengerbot.BuildOption
import io.github.jisungbin.gitmessengerbot.R
import io.github.jisungbin.gitmessengerbot.activity.main.MainActivity
import io.github.jisungbin.gitmessengerbot.activity.setup.SetupActivity
import io.github.jisungbin.gitmessengerbot.common.constant.GithubConstant
import io.github.jisungbin.gitmessengerbot.common.core.Storage
import io.github.jisungbin.gitmessengerbot.common.extension.doDelay
import io.github.jisungbin.gitmessengerbot.common.extension.toast
import io.github.jisungbin.gitmessengerbot.test.TestActivity
import io.github.jisungbin.gitmessengerbot.theme.MaterialTheme
import io.github.jisungbin.gitmessengerbot.theme.SystemUiController
import io.github.jisungbin.gitmessengerbot.theme.colors
import java.util.Calendar

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setSystemBarsColor(colors.primary)
        setContent {
            MaterialTheme {
                Content()
            }
        }

        val isSetupDone = Storage.read(GithubConstant.DataPath, null) != null
        val builtDate = Calendar.getInstance().apply { timeInMillis = BuildConfig.TIMESTAMP }
        val builtTime = "${builtDate.get(Calendar.HOUR_OF_DAY)}h" +
            " ${builtDate.get(Calendar.MINUTE)}m " +
            "${builtDate.get(Calendar.SECOND)}s"

        toast("Built at: $builtTime", Toast.LENGTH_LONG)

        if (BuildOption.TestMode) {
            startActivity(Intent(this, TestActivity::class.java))
        } else {
            lifecycleScope.launchWhenCreated {
                doDelay(2000) {
                    finish()
                    startActivity(
                        Intent(
                            this@SplashActivity,
                            if (isSetupDone) MainActivity::class.java else SetupActivity::class.java
                        )
                    )
                }
            }
        }
    }

    @Composable
    private fun Content() {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.primary)
                .padding(30.dp)
        ) {
            val footer = createRef()

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
                modifier = Modifier.constrainAs(footer) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            )
        }
    }

    override fun onBackPressed() {}
}
