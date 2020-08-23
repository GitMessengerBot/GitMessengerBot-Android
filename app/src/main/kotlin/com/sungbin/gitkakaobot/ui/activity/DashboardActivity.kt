package com.sungbin.gitkakaobot.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.ui.fragment.bot.BotFragment
import com.sungbin.gitkakaobot.util.OnBackPressedUtil
import com.sungbin.gitkakaobot.util.manager.PathManager
import com.sungbin.sungbintool.StorageUtils


/**
 * Created by SungBin on 2020-08-23.
 */

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        StorageUtils.createFolder(PathManager.JS, true)
        StorageUtils.createFolder(PathManager.SIMPLE, true)
        StorageUtils.createFolder(PathManager.DATABASE, true)
        StorageUtils.createFolder(PathManager.LOG, true)
        StorageUtils.createFolder(PathManager.SENDER, true)
        StorageUtils.createFolder(PathManager.ROOM, true)

        supportFragmentManager.commitNow {
            add(R.id.fl_container, BotFragment.instance())
        }
    }

    override fun onBackPressed() {
        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.fl_container)
        (fragment as? OnBackPressedUtil)?.onBackPressed(this)
    }

}