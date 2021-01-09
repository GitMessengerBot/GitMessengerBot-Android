package com.sungbin.gitkakaobot.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.PowerManager
import com.sungbin.androidutils.util.NotificationUtil
import com.sungbin.gitkakaobot.R


/**
 * Created by SungBin on 2020-12-14.
 */

class ForegroundService : Service() {

    private val pm by lazy { getSystemService(Context.POWER_SERVICE) as PowerManager }
    private val wakeLock by lazy { pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, wakeLockLabel) }
    private val wakeLockLabel = getString(R.string.app_name)
    private val notificationId = 1000
    private val notification by lazy {
        NotificationUtil.getNormalNotification(
            applicationContext,
            getString(R.string.app_name),
            getString(R.string.foregroundservice_running_bot),
            R.mipmap.ic_launcher,
            true
        )
    }

    override fun onBind(intent: Intent?): IBinder? = null

    @SuppressLint("WakelockTimeout")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(notificationId, notification.build())
        wakeLock.acquire()
        return START_STICKY
    }

    override fun onDestroy() {
        stopForeground(true)
        wakeLock.release()
    }

}