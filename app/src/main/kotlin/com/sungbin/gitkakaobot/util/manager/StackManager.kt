package com.sungbin.gitkakaobot.util.manager

import android.app.Notification
import com.eclipsesource.v8.V8

/**
 * Created by SungBin on 2020-08-22.
 */

object StackManager {

    val sessions = HashMap<String, Notification.Action>()
    val v8 = HashMap<String, V8>()

}