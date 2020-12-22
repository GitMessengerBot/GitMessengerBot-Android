package com.sungbin.gitkakaobot.util.manager

import android.app.Notification
import com.eclipsesource.v8.V8
import org.mozilla.javascript.Function
import org.mozilla.javascript.ScriptableObject

/**
 * Created by SungBin on 2020-08-22.
 */

object StackManager {

    val sessions = HashMap<String, Notification.Action>()
    val scopes = HashMap<String, ScriptableObject>()
    val functions = HashMap<String, Function>()
    val v8 = HashMap<String, V8>()

}