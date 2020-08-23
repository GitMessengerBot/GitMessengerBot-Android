package com.sungbin.gitkakaobot.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.ui.fragment.bot.BotFragment


/**
 * Created by SungBin on 2020-08-23.
 */

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        supportFragmentManager.commitNow {
            add(R.id.fl_container, BotFragment.instance())
        }
    }

}