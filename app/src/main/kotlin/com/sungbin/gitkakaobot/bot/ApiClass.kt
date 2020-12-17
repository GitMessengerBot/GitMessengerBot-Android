package com.sungbin.gitkakaobot.bot

import android.content.Context
import com.sungbin.androidutils.util.StorageUtil
import com.sungbin.gitkakaobot.util.manager.StackManager
import org.mozilla.javascript.ScriptableObject
import org.mozilla.javascript.annotations.JSStaticFunction
import java.text.SimpleDateFormat
import java.util.*

object ApiClass {

    private lateinit var context: Context
    fun init(context: Context) {
        ApiClass.context = context
    }

    class Log : ScriptableObject() {
        override fun getClassName() = "Log"

        companion object {
            @JvmStatic
            @JSStaticFunction
            fun e(name: String, content: String) {
                val now = System.currentTimeMillis()
                val date = Date(now)
                val sdf = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
                val time = sdf.format(date)

                // LogUtils.save(name, content, time, LogUtils.Type.ERROR)
            }

            @JvmStatic
            @JSStaticFunction
            fun i(name: String, content: String) {
                val now = System.currentTimeMillis()
                val date = Date(now)
                val sdf = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
                val time = sdf.format(date)

                // LogUtils.save(name, content, time, LogUtils.Type.INFO)
            }

            @JvmStatic
            @JSStaticFunction
            fun s(name: String, content: String) {
                val now = System.currentTimeMillis()
                val date = Date(now)
                val sdf = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
                val time = sdf.format(date)

                // LogUtils.save(name, content, time, LogUtils.Type.SUCCESS)
            }

        }
    }

    class Api : ScriptableObject() {
        override fun getClassName() = "Api"

        companion object {
            @JvmStatic
            @JSStaticFunction
            fun getContext(): Context {
                return context
            }

            /*@JvmStatic
            @JSStaticFunction
            fun replyRoom(room: String, msg: String): Boolean {
                return com.sungbin.autoreply.bot.three.api.Api.replyRoom(room, msg)
            }

            @JvmStatic
            @JSStaticFunction
            fun replyRoomShowAll(room: String, msg1: String, msg2: String): Boolean {
                return com.sungbin.autoreply.bot.three.api.Api.replyRoomShowAll(room, msg1, msg2)
            }*/
        }
    }

    class Scope : ScriptableObject() {
        override fun getClassName() = "Scope"

        companion object {
            @JvmStatic
            @JSStaticFunction
            fun get(name: String) = StackManager.scopes[name]
        }
    }

    class File : ScriptableObject() {
        override fun getClassName() = "File"

        companion object {
            @JvmStatic
            @JSStaticFunction
            fun read(path: String, _null: String?) = StorageUtil.read(path, _null)

            @JvmStatic
            @JSStaticFunction
            fun save(path: String, content: String) = StorageUtil.save(path, content)

            @JvmStatic
            @JSStaticFunction
            fun append(path: String, content: String): Boolean {
                val string = "${StorageUtil.read(path, "")}$content"
                return save(path, string)
            }

            @JvmStatic
            @JSStaticFunction
            fun delete(path: String) = StorageUtil.delete(path)

            @JvmStatic
            @JSStaticFunction
            fun deleteAll(path: String) = StorageUtil.deleteAll(path)
        }
    }

    // todo: Api, UiUtil
}