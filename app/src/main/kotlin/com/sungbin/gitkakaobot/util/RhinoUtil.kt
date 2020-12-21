package com.sungbin.gitkakaobot.util

import com.balsikandar.crashreporter.CrashReporter
import com.faendir.rhino_android.RhinoAndroidHelper
import com.sungbin.gitkakaobot.bot.ApiClass
import org.mozilla.javascript.ScriptableObject

object RhinoUtil {

    fun runJs(source: String): String {
        return try {
            val rhino = RhinoAndroidHelper().enterContext()
            rhino.languageVersion = org.mozilla.javascript.Context.VERSION_ES6
            rhino.optimizationLevel = -1

            val scope = rhino.initStandardObjects()
            ScriptableObject.defineClass(scope, ApiClass.Log::class.java, false, true)
            ScriptableObject.defineClass(scope, ApiClass.Api::class.java, false, true)
            ScriptableObject.defineClass(scope, ApiClass.Scope::class.java, false, true)
            ScriptableObject.defineClass(scope, ApiClass.File::class.java, false, true)

            val result: Any = rhino.evaluateString(scope, source, "sandbox", 1, null)
            org.mozilla.javascript.Context.exit()
            result.toString()
        } catch (exception: Exception) {
            CrashReporter.logException(exception)
            exception.toString()
        }
    }

}