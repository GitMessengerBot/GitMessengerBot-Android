package com.sungbin.gitkakaobot.util

import android.content.Context
import androidx.core.content.edit

/**
 * Created by SungBin on 2020-08-23.
 */

object DataUtil {

    fun save(context: Context, title: String, value: String) {
        context.getSharedPreferences("pref", Context.MODE_PRIVATE).edit {
            putString(title, value)
            apply()
        }
    }

    fun read(context: Context, title: String, _null: String) =
        context.getSharedPreferences("pref", Context.MODE_PRIVATE).getString(title, _null)

}