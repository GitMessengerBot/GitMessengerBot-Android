package com.sungbin.gitkakaobot.ui.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.sungbin.androidutils.extensions.doDelay
import com.sungbin.androidutils.util.DataUtil
import com.sungbin.gitkakaobot.databinding.ActivitySplashBinding
import com.sungbin.gitkakaobot.util.BotUtil
import com.sungbin.gitkakaobot.util.manager.PathManager
import org.jetbrains.anko.startActivity


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
