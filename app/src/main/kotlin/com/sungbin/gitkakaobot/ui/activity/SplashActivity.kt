package com.sungbin.gitkakaobot.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sungbin.gitkakaobot.databinding.ActivitySplashBinding
import com.sungbin.gitkakaobot.util.BotUtil
import com.sungbin.gitkakaobot.util.manager.PathManager


/**
 * Created by SungBin on 2020-08-23.
 */

class SplashActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContentView(binding.root)

        if (DataUtil.readData(applicationContext, PathManager.TOKEN, "null") != "null") {
            Thread { BotUtil.initBotList() }.start()
        }

        doDelay(1500) {
            finish()
            if (DataUtil.readData(applicationContext, PathManager.TOKEN, "null") == "null") {
                startActivity<JoinActivity>()
            } else {
                startActivity<DashboardActivity>()
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    override fun onBackPressed() {}
}

/*override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
        ScriptLayout()
    }
}

@Preview("Script")
@Composable
fun ScriptLayout() {
    Surface(
        color = colorResource(id = R.color.colorBackgroundWhite),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Row {
            Image(
                bitmap = imageResource(id = R.drawable.bg_left_round_8),
                modifier = Modifier.fillMaxHeight().width(10.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    // .padding(top = 8.dp, bottom = 8.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shadow(4.dp)
                    .background(colorResource(id = R.color.colorWhite))
                    .clickable(onClick = {
                        UiUtil.toast(applicationContext, "AAAAA")
                    })
                    .padding(4.dp),
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        imageVector = vectorResource(id = R.drawable.ic_baseline_javascript_24),
                        modifier = Modifier.clip(RoundedCornerShape(8.dp))
                            .size(40.dp)
                    )
                    Text(
                        text = "This is JavaScript Logo!",
                        modifier = Modifier.padding(start = 16.dp)
                            .align(Alignment.CenterVertically),
                        fontSize = TextUnit.Sp(18),
                        fontFamily = fontFamily(font(R.font.nanumgothic))
                    )
                    Switch(checked = false, onCheckedChange = {
                        UiUtil.toast(applicationContext, it.toString())
                    }, modifier = Modifier.align(Alignment.CenterVertically))
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "AAAAAAA", modifier = Modifier.alignByBaseline())
                }
            }
        }
    }
}*/