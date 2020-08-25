package com.sungbin.gitkakaobot.util

import android.widget.TextView
import com.faendir.rhino_android.RhinoAndroidHelper
import com.sungbin.sungbintool.extensions.toEditable
import org.mozilla.javascript.Context
import org.mozilla.javascript.Function
import org.mozilla.javascript.ScriptableObject
import org.mozilla.javascript.annotations.JSStaticFunction

object RhinoUtil {

    lateinit var debugView: TextView
    val eventFunction = HashMap<Int, Function>()

    object Event {
        const val ERROR = -1
        const val RESPONSE = 0
        const val COMPILE_START = 1
        const val COMPILE_END = 2
    }

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
                eventFunction[event] = function
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
        try {
            this.debugView = debugView

            val rhino = RhinoAndroidHelper().enterContext().apply {
                languageVersion = Context.VERSION_ES6
                optimizationLevel = -1
            }

            val scope = rhino.initStandardObjects()

            ScriptableObject.defineClass(scope, Bot::class.java, false, true)
            ScriptableObject.defineClass(scope, console::class.java, false, true)
            ScriptableObject.defineProperty(scope, "Event", Event, 0)
            rhino.compileString(code, "debug", 1, null).exec(rhino, scope)
            eventFunction[Event.RESPONSE]?.call(
                rhino,
                scope,
                scope,
                arrayOf("API2 TEST")
            )
            Context.exit()
        } catch (e: Exception) {
            debugView.text = e.message.toString().replace("RhinoUtil.kt", "Rhino-Debug").toEditable()
        }
    }

}
