package com.sungbin.gitkakaobot.util

import android.widget.TextView
import com.faendir.rhino_android.RhinoAndroidHelper
import com.sungbin.gitkakaobot.model.BotItem
import com.sungbin.gitkakaobot.model.BotType
import com.sungbin.gitkakaobot.util.BotUtil.functions
import com.sungbin.sungbintool.extensions.clear
import com.sungbin.sungbintool.extensions.plusAssign
import com.sungbin.sungbintool.extensions.toEditable
import org.mozilla.javascript.Context
import org.mozilla.javascript.Function
import org.mozilla.javascript.ImporterTopLevel
import org.mozilla.javascript.ScriptableObject
import org.mozilla.javascript.annotations.JSFunction

object RhinoUtil {

    lateinit var debugView: TextView

    fun <T : ScriptableObject> T.conv(predicate: ((String) -> Boolean)? = null): T {
        val names = getAllJsFunctions(this::class.java).run {
            val temp = ArrayList<String>()
            forEach {
                if (predicate == null || predicate(it)) temp.add(it)
            }
            return@run temp.toTypedArray()
        }
        this.defineFunctionProperties(names, this::class.java, ScriptableObject.EMPTY)
        return this
    }

    private fun getAllJsFunctions(clazz: Class<out ScriptableObject>): Array<String> {
        val allList = ArrayList<String>()
        for (element in clazz.methods) {
            element.getAnnotation(JSFunction::class.java)?.let {
                allList.add(element.name)
            }
        }
        return allList.toTypedArray()
    }

    class Bot(private var code: String) : ScriptableObject() {
        override fun getClassName() = "Bot"
        override fun toString() = "TODO"

        @JSFunction
        fun getTestMessage() = "TEST-MESSAGE"

        @JSFunction
        fun getTestValue(any: Any) = any

        @JSFunction
        fun getCurrentBot() = BotItem("DEBUG", true, true, BotType.DEBUG, -1, "null", 0, "-1")

        @JSFunction
        fun on(event: Int, function: Function) {
            addListener(event, function)
        }

        @JSFunction
        fun addListener(event: Int, function: Function) {
            functions["-1"] = hashMapOf(event to function)
        }
    }

    class console : ScriptableObject() {
        override fun getClassName() = "console"
        override fun toString() = "TODO"

        @JSFunction
        fun log(string: String) {
            debugView.text = string.toEditable()
        }
    }

    fun debug(code: String, debugView: TextView) {
        this.debugView = debugView
        debugView.clear()
        val rhino = RhinoAndroidHelper().enterContext().apply {
            languageVersion = Context.VERSION_ES6
            optimizationLevel = -1
        }
        val scope = rhino.initStandardObjects(ImporterTopLevel(rhino)) as ScriptableObject
        try {
            ScriptableObject.defineProperty(scope, "Event", BotUtil.Event, 0)
            ScriptableObject.defineProperty(scope, "Bot", Bot(code).conv(), 0)
            ScriptableObject.defineProperty(scope, "console", console().conv(), 0)
            /*ScriptableObject.defineClass(scope, ApiClass.Log::class.java, false, true)
            ScriptableObject.defineClass(scope, ApiClass.AppData::class.java, false, true)
            ScriptableObject.defineClass(scope, ApiClass.Api::class.java, false, true)
            ScriptableObject.defineClass(scope, ApiClass.Device::class.java, false, true)
            ScriptableObject.defineClass(scope, ApiClass.Scope::class.java, false, true)
            ScriptableObject.defineClass(scope, ApiClass.File::class.java, false, true)
            ScriptableObject.defineClass(scope, ApiClass.DataBase::class.java, false, true)
            ScriptableObject.defineClass(scope, ApiClass.Black::class.java, false, true)
            ScriptableObject.defineClass(scope, ApiClass.Game::class.java, false, true)
            ScriptableObject.defineClass(scope, ApiClass.Util::class.java, false, true)*/
            rhino.compileString(code, "DEBUG", 1, null).exec(rhino, scope)
            /*val result = rhino.evaluateString(scope, code, "DEBUG", 1, null)
            functions["-1"]!![BotUtil.Event.DEBUG]?.call(
                rhino,
                scope,
                scope,
                arrayOf(result)
            )*/
            Context.exit()
        } catch (e: Exception) {
            /*functions["-1"]?.get(BotUtil.Event.ERROR)?.call(
                rhino,
                scope,
                scope,
                arrayOf("DEBUG", e)
            )*/
            debugView += e.toString()
        }
    }

}
