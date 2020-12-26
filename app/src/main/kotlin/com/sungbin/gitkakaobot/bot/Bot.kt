package com.sungbin.gitkakaobot.bot

import android.app.Notification
import android.app.RemoteInput
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.bot.v8.V8Log
import com.sungbin.gitkakaobot.bot.v8.V8Replier
import com.sungbin.gitkakaobot.model.Bot
import com.sungbin.gitkakaobot.model.BotCompile
import com.sungbin.gitkakaobot.util.BotUtil
import com.sungbin.gitkakaobot.util.UiUtil
import com.sungbin.gitkakaobot.util.manager.StackManager
import com.sungbin.gitkakaobot.util.toBase64String


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
            /*if (bot.type == BotType.RHINOJS) { // todo: 라이노 지원을 해야 할 까?
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
            } else { // v8 js*/
            val v8 = V8.createV8Runtime()
            val v8Replier = V8Object(v8)
            v8.add("v8Replier", v8Replier)
            v8Replier.registerJavaMethod(
                V8Replier(),
                "reply",
                "reply",
                arrayOf(String::class.java, String::class.java)
            )
            v8Replier.release() // todo: deprecated? 그럼 다른거 뭐 써야하는데!!
            val v8Log = V8Object(v8)
            v8.add("v8Log", v8Log)
            v8Log.registerJavaMethod(
                V8Log(),
                "log",
                "log",
                arrayOf(String::class.java)
            )
            v8Log.release() // todo: deprecated? 그럼 다른거 뭐 써야하는데!!
            v8.executeScript(BotUtil.getBotCode(bot))
            StackManager.v8[bot.uuid] = v8
            v8.locker.release()
            BotCompile(true, null)
        } catch (exception: Exception) {
            BotCompile(false, exception)
        }
    }

    fun callJsResponder(
        bot: Bot,
        room: String,
        message: String,
        sender: String,
        isGroupChat: Boolean,
        session: Notification.Action?,
        profileImage: Bitmap?,
        packageName: String,
        isDebugMode: Boolean
    ) {
        try {
            /*if (bot.type == BotType.RHINOJS) {
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
                            room, message, sender, isGroupChat,
                            Replier(session),
                            ImageDB(profileImage), packageName
                        )
                    )
                } else {
                    // todo: 디버그 모드
                }
                org.mozilla.javascript.Context.exit()
            } else { // V8 JS*/
            val v8 = StackManager.v8[bot.uuid]!!
            v8.locker.acquire()
            val arguments = V8Object(v8).run {
                add("room", room)
                add("message", message)
                add("sender", sender)
                add("isGroupChat", isGroupChat)
                add("profileImage", profileImage.toBase64String())
                add("packageName", packageName)
            }
            /*v8.executeJSFunction( // 안되는거 확인
                "response", room, message, sender, isGroupChat,
                Replier(session),
                ImageDB(profileImage), packageName
            )*/
            v8.executeJSFunction("onMessage", arguments)
            v8.locker.release()
        } catch (exception: Exception) {
            // todo: 오류처리
        }
    }

}