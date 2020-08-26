package com.sungbin.gitkakaobot.util.api

import android.content.Context
import androidx.core.content.edit

object AppData {
    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }

    fun getInt(name: String, _null: Int) = context.getSharedPreferences(
        "AppData",
        Context.MODE_PRIVATE
    ).getInt(name, _null)

    fun getBoolean(name: String, _null: Boolean) = context.getSharedPreferences(
        "AppData",
        Context.MODE_PRIVATE
    ).getBoolean(name, _null)

    fun getString(name: String, _null: String) = context.getSharedPreferences(
        "AppData",
        Context.MODE_PRIVATE
    ).getString(name, _null)

    fun putString(name: String, data: String) {
        context.getSharedPreferences(
            "AppData",
            Context.MODE_PRIVATE
        ).edit {
            putString(name, data)
            apply()
        }
    }

    fun putInt(name: String, data: Int) {
        context.getSharedPreferences(
            "AppData",
            Context.MODE_PRIVATE
        ).edit {
            putInt(name, data)
            apply()
        }
    }

    fun putBoolean(name: String, data: Boolean) {
        context.getSharedPreferences(
            "AppData",
            Context.MODE_PRIVATE
        ).edit {
            putBoolean(name, data)
            apply()
        }
    }

    fun remove(name: String) {
        context.getSharedPreferences(
            "AppData",
            Context.MODE_PRIVATE
        ).edit {
            remove(name)
            apply()
        }
    }

    fun clear() {
        context.getSharedPreferences(
            "AppData",
            Context.MODE_PRIVATE
        ).edit {
            clear()
            apply()
        }
    }
}