package com.sungbin.gitkakaobot.util

import android.content.Context
import com.eclipsesource.v8.V8
import com.faendir.rhino_android.RhinoAndroidHelper
import com.sungbin.gitkakaobot.bot.rhino.ApiClass
import org.mozilla.javascript.ScriptableObject

object JsUtil {

    lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }

    fun runRhino(source: String): String {
        return try {
            val rhino = RhinoAndroidHelper(context).enterContext().apply {
                languageVersion = org.mozilla.javascript.Context.VERSION_ES6
                optimizationLevel = -1
            }

            val scope = rhino.initStandardObjects()
            ScriptableObject.defineClass(scope, ApiClass.App::class.java, false, true)
            val result = rhino.evaluateString(scope, source, "rhino-runtime", 1, null)
            org.mozilla.javascript.Context.exit()
            result.toString()
        } catch (exception: Exception) {
            exception.toString()
        }
    }

    fun runV8(source: String): String {
        val v8 = V8.createV8Runtime()
        return try {
            v8.executeScript(source)
            v8.toString()
        } catch (exception: Exception) {
            exception.toString()
        } finally {
            v8.release(false)
        }
    }

}