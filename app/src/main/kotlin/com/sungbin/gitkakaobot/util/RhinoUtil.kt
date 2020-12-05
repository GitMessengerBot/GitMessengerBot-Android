package com.sungbin.gitkakaobot.util

import android.widget.TextView
import com.faendir.rhino_android.RhinoAndroidHelper
import com.sungbin.gitkakaobot.listener.MessageListener
import com.sungbin.gitkakaobot.util.BotUtil.functions
import com.sungbin.gitkakaobot.util.api.ApiClass
import com.sungbin.sungbintool.extensions.clear
import org.mozilla.javascript.Context
import org.mozilla.javascript.ImporterTopLevel
import org.mozilla.javascript.ScriptableObject

object RhinoUtil {
    fun debug(code: String, view: TextView) {
        if (!::debugView.isInitialized) {
            ApiClass.init(view.context)
            debugView = view
        }
        view.clear()
        val rhino = RhinoAndroidHelper().enterContext().apply {
            languageVersion = Context.VERSION_ES6
            optimizationLevel = -1
        }
        val scope = rhino.initStandardObjects(ImporterTopLevel(rhino)) as ScriptableObject
        try {
            ScriptableObject.defineProperty(scope, "Event", BotUtil.Event, 0)
            ScriptableObject.defineProperty(
                scope, "BotManager", MessageListener.Companion.BotManager()
                    .conv(), 0
            )
            ScriptableObject.defineProperty(scope, "console", console().conv(), 0)
            ScriptableObject.defineProperty(scope, "Log", ApiClass.Log().conv(), 0)
            ScriptableObject.defineProperty(scope, "AppData", ApiClass.AppData().conv(), 0)
            ScriptableObject.defineProperty(scope, "Api", ApiClass.Api().conv(), 0)
            ScriptableObject.defineProperty(scope, "Device", ApiClass.Device().conv(), 0)
            ScriptableObject.defineProperty(scope, "Scope", ApiClass.Scope().conv(), 0)
            ScriptableObject.defineProperty(scope, "File", ApiClass.File().conv(), 0)
            ScriptableObject.defineProperty(scope, "DataBase", ApiClass.DataBase().conv(), 0)
            ScriptableObject.defineProperty(scope, "Black", ApiClass.Black().conv(), 0)
            ScriptableObject.defineProperty(scope, "Game", ApiClass.Game().conv(), 0)
            ScriptableObject.defineProperty(scope, "Util", ApiClass.Util().conv(), 0)
            val value = rhino.compileString(code, "DEBUG", 1, null).exec(rhino, scope)
            functions["-1"]?.get(BotUtil.Event.DEBUG)?.call(
                rhino,
                scope,
                scope,
                arrayOf(value)
            )
            Context.exit()
        } catch (e: Exception) {
            functions["-1"]?.get(BotUtil.Event.ERROR)?.call(
                rhino,
                scope,
                scope,
                arrayOf("DEBUG", e)
            )
            e.printStackTrace()
        }
    }

}
