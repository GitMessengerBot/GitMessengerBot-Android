/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.theme.BindView
import me.sungbin.gitmessengerbot.theme.SystemUiController
import me.sungbin.gitmessengerbot.theme.colors

/**
 * Created by SungBin on 2021/04/08.
 */

class SetupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SystemUiController(window).setSystemBarsColor(colors.primary)

        setContent {
            BindView {
                SetupView()
            }
        }
    }

    @Preview
    @Composable
    private fun SetupView() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colors.primary)
                .padding(30.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_round_warning_amber_24),
                    modifier = Modifier.size(80.dp),
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(R.string.setup_title),
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                PermissionView("저장소 접근 권한 (필수)", "소스코드 저장을 위해 해당 권한이 필요합니다.", listOf(0, 16))
                PermissionView("알림 읽기 권한 (필수)", "카카오톡 알림을 감지하기 위해 필요합니다.", listOf(16, 16))
                PermissionView("배터리 최적화 제외 (선택)", "봇이 빠른속도로 동작하기 위해 필요합니다.", listOf(16, 0))
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "마지막으로,",
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Column(
                    modifier = Modifier.background(
                        color = colors.primaryVariant,
                        RoundedCornerShape(15.dp)
                    )
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        text = "개인 키 입력 후 시작하기",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }

    @Composable
    private fun PermissionView(name: String, description: String, padding: List<Int>) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = padding[0].dp, bottom = padding[1].dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White, RoundedCornerShape(15.dp))
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_baseline_error_24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.Black)
                )
                Text(
                    text = name,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                color = Color.White,
                text = description,
                textAlign = TextAlign.Center,
                fontSize = 13.sp
            )
        }
    }
}
