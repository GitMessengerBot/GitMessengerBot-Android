package com.sungbin.gitkakaobot.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.util.DataUtil
import com.sungbin.gitkakaobot.util.manager.PathManager
import org.jetbrains.anko.startActivity


/**
 * Created by SungBin on 2020-08-23.
 */

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            finish()
            if (DataUtil.read(applicationContext, PathManager.TOKEN, "null") == "null") {
                startActivity<JoinActivity>()
            } else {
                startActivity<DashboardActivity>()
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }, 3000)
    }

    override fun onBackPressed() {}
}
