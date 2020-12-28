package com.sungbin.gitkakaobot.bot.rhino

import android.content.Context
import org.mozilla.javascript.ScriptableObject
import org.mozilla.javascript.annotations.JSStaticFunction

object ApiClass {

    private lateinit var context: Context
    fun init(context: Context) {
        this.context = context
    }

    class Api : ScriptableObject() {
        override fun getClassName() = "Api"

        companion object {
            @JvmStatic
            @JSStaticFunction
            fun getContext(): Context {
                return context
            }
        }
    }

}