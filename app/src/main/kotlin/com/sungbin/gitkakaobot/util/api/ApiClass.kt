package com.sungbin.gitkakaobot.util.api

import android.content.Context
import com.sungbin.gitkakaobot.listener.MessageListener
import com.sungbin.gitkakaobot.util.UiUtil
import com.sungbin.gitkakaobot.util.api.game.chosung.ChosungType
import com.sungbin.gitkakaobot.util.manager.PathManager.DATABASE
import com.sungbin.gitkakaobot.util.manager.StackManager.scopes
import com.sungbin.sungbintool.StorageUtils
import org.mozilla.javascript.NativeArray
import org.mozilla.javascript.ScriptableObject
import org.mozilla.javascript.annotations.JSFunction
import java.text.SimpleDateFormat
import java.util.*

object ApiClass {

    private lateinit var context: Context
    fun init(context: Context) {
        context.let {
            this.context = it
            com.sungbin.gitkakaobot.util.api.Api.init(it)
            com.sungbin.gitkakaobot.util.api.AppData.init(it)
            com.sungbin.gitkakaobot.util.api.Black.init(it)
            com.sungbin.gitkakaobot.util.api.Device.init(it)
            com.sungbin.gitkakaobot.util.api.Util.init(it)
        }
    }

    class Log : ScriptableObject() {
        override fun getClassName() = "Log"

        @JSFunction
        fun d(name: String, content: String) {
            val now = System.currentTimeMillis()
            val date = Date(now)
            val sdf = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
            val time = sdf.format(date)

            //LogUtils.save(name, content, time, LogUtils.Type.DEBUG)
        }

        @JSFunction
        fun e(name: String, content: String) {
            val now = System.currentTimeMillis()
            val date = Date(now)
            val sdf = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
            val time = sdf.format(date)

            //LogUtils.save(name, content, time, LogUtils.Type.ERROR)
        }

        @JSFunction
        fun i(name: String, content: String) {
            val now = System.currentTimeMillis()
            val date = Date(now)
            val sdf = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
            val time = sdf.format(date)

            //LogUtils.save(name, content, time, LogUtils.Type.INFO)
        }

        @JSFunction
        fun s(name: String, content: String) {
            val now = System.currentTimeMillis()
            val date = Date(now)
            val sdf = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
            val time = sdf.format(date)

            //LogUtils.save(name, content, time, LogUtils.Type.SUCCESS)
        }
    }

    class AppData : ScriptableObject() {
        override fun getClassName() = "AppData"

        @JSFunction
        fun putInt(name: String, value: Int) {
            com.sungbin.gitkakaobot.util.api.AppData.putInt(name, value)
        }

        @JSFunction
        fun putString(name: String, value: String) {
            com.sungbin.gitkakaobot.util.api.AppData.putString(name, value)
        }

        @JSFunction
        fun putBoolean(name: String, value: Boolean) {
            com.sungbin.gitkakaobot.util.api.AppData.putBoolean(name, value)
        }

        @JSFunction
        fun getInt(name: String, value: Int) =
            com.sungbin.gitkakaobot.util.api.AppData.getInt(name, value)

        @JSFunction
        fun getString(name: String, value: String) =
            com.sungbin.gitkakaobot.util.api.AppData.getString(name, value)

        @JSFunction
        fun getBoolean(name: String, value: Boolean) =
            com.sungbin.gitkakaobot.util.api.AppData.getBoolean(name, value)

        @JSFunction
        fun clear() {
            com.sungbin.gitkakaobot.util.api.AppData.clear()
        }

        @JSFunction
        fun remove(name: String) {
            com.sungbin.gitkakaobot.util.api.AppData.remove(name)
        }
    }

    class Api : ScriptableObject() {
        override fun getClassName() = "Api"

        @JSFunction
        fun getContext() = context

        @JSFunction
        fun replyRoom(room: String, msg: String) =
            com.sungbin.gitkakaobot.util.api.Api.replyRoom(room, msg)

        @JSFunction
        fun replyRoomShowAll(room: String, msg1: String, msg2: String) =
            com.sungbin.gitkakaobot.util.api.Api.replyRoomShowAll(room, msg1, msg2)
    }

    class Game : ScriptableObject() {
        override fun getClassName() = "Game"

        @JSFunction
        fun getRandomChosungQuiz() =
            com.sungbin.gitkakaobot.util.api.Game.chosungQuiz(ChosungType.getRandom())

        @JSFunction
        fun getChosungQuiz(type: Int) =
            com.sungbin.gitkakaobot.util.api.Game.chosungQuiz(type)
    }


    class Device : ScriptableObject() {
        override fun getClassName() = "Device"

        @JSFunction
        fun getBattery() = com.sungbin.gitkakaobot.util.api.Device.battery

        @JSFunction
        fun getPhoneModel() = com.sungbin.gitkakaobot.util.api.Device.phoneModel

        @JSFunction
        fun getAndroidSDKVersion() =
            com.sungbin.gitkakaobot.util.api.Device.androidVersion

        @JSFunction
        fun getAndroidVersion() =
            com.sungbin.gitkakaobot.util.api.Device.androidVersion

        @JSFunction
        fun getIsCharging() = com.sungbin.gitkakaobot.util.api.Device.isCharging
    }

    class Scope : ScriptableObject() {
        override fun getClassName() = "Scope"

        @JSFunction
        fun get(name: String) = scopes[name]
    }

    class DataBase : ScriptableObject() {
        override fun getClassName() = "DataBase"

        @JSFunction
        fun read(name: String) = StorageUtils.read("$DATABASE/$name", null)

        @JSFunction
        fun save(name: String, content: String) = StorageUtils.save("$DATABASE/$name", content)

        @JSFunction
        fun remove(name: String) = StorageUtils.delete("$DATABASE/$name")
    }

    class File : ScriptableObject() {
        override fun getClassName() = "File"

        @JSFunction
        //@JvmOverloads
        fun read(
            path: String,
            _null: String?,
            autoInputSdcardPath: Boolean = false
        ) = StorageUtils.read(path, _null, autoInputSdcardPath)

        @JSFunction
        //@JvmOverloads
        fun save(
            path: String,
            content: String,
            autoInputSdcardPath: Boolean = false
        ) = StorageUtils.save(path, content, autoInputSdcardPath)

        @JSFunction
        //@JvmOverloads
        fun append(
            path: String,
            content: String,
            autoInputSdcardPath: Boolean = false
        ) = StorageUtils.save(
            path,
            "${StorageUtils.read(path, "")}$content",
            autoInputSdcardPath
        )

        @JSFunction
        //@JvmOverloads
        fun delete(path: String, autoInputSdcardPath: Boolean = false) =
            StorageUtils.delete(path, autoInputSdcardPath)

        @JSFunction
        //@JvmOverloads
        fun deleteAll(path: String, autoInputSdcardPath: Boolean = false) =
            StorageUtils.deleteAll(path, autoInputSdcardPath)
    }

    class Black : ScriptableObject() {
        override fun getClassName() = "Black"

        @JSFunction
        fun getSender() = com.sungbin.gitkakaobot.util.api.Black.readSender()

        @JSFunction
        fun getRoom() = com.sungbin.gitkakaobot.util.api.Black.readRoom()

        @JSFunction
        fun addRoom(room: String) {
            com.sungbin.gitkakaobot.util.api.Black.addRoom(room)
        }

        @JSFunction
        fun addSender(sender: String) {
            com.sungbin.gitkakaobot.util.api.Black.addSender(sender)
        }

        @JSFunction
        fun removeRoom(room: String) {
            com.sungbin.gitkakaobot.util.api.Black.removeRoom(room)
        }

        @JSFunction
        fun removeSender(sender: String) {
            com.sungbin.gitkakaobot.util.api.Black.removeSender(sender)
        }
    }

    class Util : ScriptableObject() {
        override fun getClassName() = "Util"

        @JSFunction
        fun makeToast(content: String) {
            UiUtil.toast(context, content)
        }

        @JSFunction
        fun delay(ms: Int) {
            Thread.sleep(ms.toLong())
        }

        @JSFunction
        fun makeNoti(title: String, content: String) {
            com.sungbin.gitkakaobot.util.api.Util.makeNoti(
                title,
                content
            )
        }

        @JSFunction
        //@JvmOverloads
        fun getHtml(link: String, fromJsoup: Boolean = true) =
            if (!fromJsoup) {
                com.sungbin.gitkakaobot.util.api.Api.getHtmlFromJava(
                    link
                )
            } else com.sungbin.gitkakaobot.util.api.Api.getHtmlFromJsoup(
                link
            )

        @JSFunction
        fun post(
            address: String,
            postName: NativeArray,
            postData: NativeArray
        ) = com.sungbin.gitkakaobot.util.api.Api.post(
            address,
            postName,
            postData
        )

        @JSFunction
        fun showAll() = MessageListener.showAll

        @JSFunction
        fun makeVibration(ms: Int) {
            com.sungbin.gitkakaobot.util.api.Util.makeVibration(ms)
        }

        @JSFunction
        fun copy(content: String) {
            com.sungbin.gitkakaobot.util.api.Util.copy(content)
        }
    }
}