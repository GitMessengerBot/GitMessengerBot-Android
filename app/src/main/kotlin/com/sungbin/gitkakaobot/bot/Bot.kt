package com.sungbin.gitkakaobot.bot

import android.app.Notification
import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import com.faendir.rhino_android.RhinoAndroidHelper
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.model.Bot
import com.sungbin.gitkakaobot.model.BotCompile
import com.sungbin.gitkakaobot.util.BotUtil
import com.sungbin.gitkakaobot.util.UiUtil
import com.sungbin.gitkakaobot.util.manager.StackManager
import org.mozilla.javascript.Function
import org.mozilla.javascript.ImporterTopLevel
import org.mozilla.javascript.ScriptableObject


/**
 * Created by SungBin on 2020-12-18.
 */

object Bot {

    private lateinit var context: Context
    fun init(context: Context) {
        this.context = context
    }

    fun replyToSession(session: Notification.Action?, value: String) {
        if (session == null) {
            UiUtil.toast(context, context.getString(R.string.bot_cant_load_session))
        } else {
            try {
                val sendIntent = Intent()
                val msg = Bundle()
                for (inputable in session.remoteInputs) msg.putCharSequence(
                    inputable.resultKey,
                    value
                )
                RemoteInput.addResultsToIntent(session.remoteInputs, sendIntent, msg)
                session.actionIntent.send(context, 0, sendIntent)
            } catch (exception: Exception) {
                UiUtil.error(context, exception)
            }
        }
    }

    fun compileJavaScript(bot: Bot): BotCompile {
        return try {
            val rhino = RhinoAndroidHelper().enterContext().apply {
                languageVersion = org.mozilla.javascript.Context.VERSION_ES6
                optimizationLevel = bot.optimization
            }
            val scope = rhino.initStandardObjects(ImporterTopLevel(rhino)) as ScriptableObject
            ScriptableObject.defineClass(scope, ApiClass.Log::class.java, false, true)
            ScriptableObject.defineClass(scope, ApiClass.Api::class.java, false, true)
            ScriptableObject.defineClass(scope, ApiClass.Scope::class.java, false, true)
            ScriptableObject.defineClass(scope, ApiClass.File::class.java, false, true)
            rhino.compileString(BotUtil.getBotCode(bot), bot.name, 1, null).exec(rhino, scope)
            val function = scope["response", scope] as Function
            StackManager.scopes[bot.uuid] = scope
            StackManager.functions[bot.uuid] = function
            org.mozilla.javascript.Context.exit()
            BotCompile(true, null)
        } catch (exception: Exception) {
            BotCompile(false, exception)
        }
    }

    fun callJsResponder(
        bot: Bot,
        room: String,
        msg: String,
        sender: String,
        isGroupChat: Boolean,
        session: Notification.Action?,
        profileImage: Bitmap?,
        packageName: String,
        isDebugMode: Boolean
    ) {
        try {
            val rhino = RhinoAndroidHelper().enterContext().apply {
                languageVersion = org.mozilla.javascript.Context.VERSION_ES6
                optimizationLevel = bot.optimization
            }
            val scope = StackManager.scopes[bot.uuid]
            val function = StackManager.functions[bot.uuid]
            if (!isDebugMode) {
                function?.call(
                    rhino,
                    scope,
                    scope,
                    arrayOf(
                        room, msg, sender, isGroupChat,
                        Replier(session),
                        ImageDB(profileImage), packageName
                    )
                )
            } else {
                // todo: 디버그 모드
            }
            org.mozilla.javascript.Context.exit()
        } catch (exception: Exception) {
            // todo: 오류처리
        }
    }

}