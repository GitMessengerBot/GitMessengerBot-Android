package com.sungbin.gitkakaobot.util

import android.widget.TextView
import com.faendir.rhino_android.RhinoAndroidHelper
import com.sungbin.gitkakaobot.util.BotUtil.functions
import com.sungbin.sungbintool.extensions.clear
import com.sungbin.sungbintool.extensions.toEditable
import org.mozilla.javascript.Context
import org.mozilla.javascript.Function
import org.mozilla.javascript.ScriptableObject
import org.mozilla.javascript.annotations.JSStaticFunction

object RhinoUtil {

    lateinit var debugView: TextView

    class Bot : ScriptableObject() {
        override fun getClassName() = "Bot"

        companion object {
            @JvmStatic
            @JSStaticFunction
            fun getTestMessage() = "TEST-MESSAGE"

            @JvmStatic
            @JSStaticFunction
            fun getTestValue(any: Any) = any

            @JvmStatic
            @JSStaticFunction
            fun addListener(event: Int, function: Function) {
                functions["-1"] = hashMapOf(event to function)
            }
        }
    }

    class console : ScriptableObject() {
        override fun getClassName() = "console"

        companion object {
            @JvmStatic
            @JSStaticFunction
            fun log(string: String) {
                debugView.text = string.toEditable()
            }
        }
    }

    fun debug(code: String, debugView: TextView) {
        this.debugView = debugView
        debugView.clear()
        val rhino = RhinoAndroidHelper().enterContext().apply {
            languageVersion = Context.VERSION_ES6
            optimizationLevel = -1
        }
        val scope = rhino.initStandardObjects()
        try {
            ScriptableObject.defineClass(scope, Bot::class.java, false, true)
            ScriptableObject.defineClass(scope, console::class.java, false, true)
            ScriptableObject.defineProperty(scope, "Event", BotUtil.Event, 0)
            rhino.compileString(code, "debug", 1, null).exec(rhino, scope)
            val result = rhino.evaluateString(scope, code, "debug", 1, null)
            functions["-1"]!![BotUtil.Event.DEBUG]?.call(
                rhino,
                scope,
                scope,
                arrayOf(result)
            )
            Context.exit()
        } catch (e: Exception) {
            functions["-1"]?.get(BotUtil.Event.ERROR)?.call(
                rhino,
                scope,
                scope,
                arrayOf("DEBUG", e)
            )
        }
    }

}
