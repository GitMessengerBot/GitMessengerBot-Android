package com.sungbin.gitkakaobot.util

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.balsikandar.crashreporter.CrashReporter
import com.google.android.material.snackbar.Snackbar
import com.sungbin.androidutils.extensions.toColorStateList
import com.sungbin.androidutils.util.ToastLength
import com.sungbin.androidutils.util.ToastType
import com.sungbin.androidutils.util.ToastUtil
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.ui.activity.ExceptionActivity
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

/**
 * Created by SungBin on 2020-08-23.
 */

object UiUtil {

    fun error(context: Context, exception: Exception) {
        CrashReporter.logException(exception)
        exception.printStackTrace()
        val message = exception.localizedMessage
        val line = exception.stackTrace[0].lineNumber
        val content = "$message #$line"
        context.startActivity(context.intentFor<ExceptionActivity>("message" to content).newTask())
    }

    fun toast(
        context: Context,
        message: String,
        toastType: ToastType = ToastType.SUCCESS,
        toastLength: ToastLength = ToastLength.SHORT
    ) {
        ToastUtil.show(context, message, toastLength, toastType)
    }

    fun snackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).apply {
            getView().backgroundTintList =
                ContextCompat.getColor(view.context, R.color.colorWhite).toColorStateList()
            setActionTextColor(ContextCompat.getColor(view.context, R.color.colorGray)).setAction(
                view.context.getString(R.string.close)
            ) {
                dismiss()
            }
            setTextColor(ContextCompat.getColor(view.context, R.color.colorBlack))
        }.show()
    }

}