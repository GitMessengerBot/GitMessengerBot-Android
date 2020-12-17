package com.sungbin.gitkakaobot.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.sungbin.androidutils.extensions.toColorStateList
import com.sungbin.gitkakaobot.R

/**
 * Created by SungBin on 2020-08-23.
 */

object UiUtil {

    fun error(context: Context, exception: Exception) {}

    fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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