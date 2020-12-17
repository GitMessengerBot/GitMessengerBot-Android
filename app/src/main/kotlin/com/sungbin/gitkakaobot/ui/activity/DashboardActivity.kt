package com.sungbin.gitkakaobot.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sungbin.androidutils.util.StorageUtil
import com.sungbin.gitkakaobot.databinding.ActivityDashboardBinding
import com.sungbin.gitkakaobot.util.manager.PathManager


/**
 * Created by SungBin on 2020-08-23.
 */

class DashboardActivity : AppCompatActivity() {

    companion object {
        lateinit var onBackPressedAction: () -> Unit
    }

    private val binding by lazy { ActivityDashboardBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StorageUtil.createFolder(PathManager.JS)
        StorageUtil.createFolder(PathManager.SIMPLE)
        StorageUtil.createFolder(PathManager.DATABASE)
        StorageUtil.createFolder(PathManager.LOG)
        StorageUtil.createFolder(PathManager.SENDER)
        StorageUtil.createFolder(PathManager.ROOM)
    }

    override fun onBackPressed() {
        onBackPressedAction()
    }

}