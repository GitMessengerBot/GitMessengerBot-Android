package me.sungbin.gitmessengerbot.ui

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.theme.BindView
import me.sungbin.gitmessengerbot.theme.SystemUiController
import me.sungbin.gitmessengerbot.theme.colors

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setSystemBarsColor(colors.primary)

        setContent {
            BindView {
                SplashView()
            }
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
                    painter = painterResource(R.mipmap.ic_launcher),
                    contentDescription = null,
                    modifier = Modifier.size(150.dp, 150.dp)
                )
                Text(
                    text = stringResource(R.string.app_name),
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 10.dp)
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
