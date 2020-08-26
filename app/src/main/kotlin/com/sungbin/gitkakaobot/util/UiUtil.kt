package com.sungbin.gitkakaobot.util

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

/**
 * Created by SungBin on 2020-08-23.
 */

object UiUtil {

    fun error(context: Context, exception: Exception) {}

    fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun snackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

}