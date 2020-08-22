package com.sungbin.gitkakaobot.util

import com.faendir.rhino_android.RhinoAndroidHelper
import org.mozilla.javascript.Context

object RhinoUtils {
    fun run(sourcecode: String): String { //자바스크립트 eval 기능하는 함수
        return try {
            val rhino = RhinoAndroidHelper().enterContext().apply {
                languageVersion = Context.VERSION_ES6
                optimizationLevel = -1
            }

            val scope = rhino.initStandardObjects()
            rhino.evaluateString(scope, sourcecode, "eval", 1, null).toString()
        }
        catch (e: Exception){
            e.message.toString()
        }
    }
}