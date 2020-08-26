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
import org.mozilla.javascript.annotations.JSStaticFunction
import java.text.SimpleDateFormat
import java.util.*

object ApiClass {

    private lateinit var context: Context
    fun setContext(ctx: Context) {
        context = ctx
    }

    class Log : ScriptableObject() {
        override fun getClassName(): String {
            return "Log"
        }

        companion object {
            @JvmStatic
            @JSStaticFunction
            fun d(name: String, content: String) {
                val now = System.currentTimeMillis()
                val date = Date(now)
                val sdf = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
                val time = sdf.format(date)

                //LogUtils.save(name, content, time, LogUtils.Type.DEBUG)
            }

            @JvmStatic
            @JSStaticFunction
            fun e(name: String, content: String) {
                val now = System.currentTimeMillis()
                val date = Date(now)
                val sdf = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
                val time = sdf.format(date)

                //LogUtils.save(name, content, time, LogUtils.Type.ERROR)
            }

            @JvmStatic
            @JSStaticFunction
            fun i(name: String, content: String) {
                val now = System.currentTimeMillis()
                val date = Date(now)
                val sdf = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
                val time = sdf.format(date)

                //LogUtils.save(name, content, time, LogUtils.Type.INFO)
            }

            @JvmStatic
            @JSStaticFunction
            fun s(name: String, content: String) {
                val now = System.currentTimeMillis()
                val date = Date(now)
                val sdf = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
                val time = sdf.format(date)

                //LogUtils.save(name, content, time, LogUtils.Type.SUCCESS)
            }

        }
    }

    class AppData : ScriptableObject() {
        override fun getClassName() = "AppData"

        companion object {
            @JvmStatic
            @JSStaticFunction
            fun putInt(name: String, value: Int) {
                com.sungbin.gitkakaobot.util.api.AppData.putInt(name, value)
            }

            @JvmStatic
            @JSStaticFunction
            fun putString(name: String, value: String) {
                com.sungbin.gitkakaobot.util.api.AppData.putString(name, value)
            }

            @JvmStatic
            @JSStaticFunction
            fun putBoolean(name: String, value: Boolean) {
                com.sungbin.gitkakaobot.util.api.AppData.putBoolean(name, value)
            }

            @JvmStatic
            @JSStaticFunction
            fun getInt(name: String, value: Int) =
                com.sungbin.gitkakaobot.util.api.AppData.getInt(name, value)

            @JvmStatic
            @JSStaticFunction
            fun getString(name: String, value: String) =
                com.sungbin.gitkakaobot.util.api.AppData.getString(name, value)

            @JvmStatic
            @JSStaticFunction
            fun getBoolean(name: String, value: Boolean) =
                com.sungbin.gitkakaobot.util.api.AppData.getBoolean(name, value)

            @JvmStatic
            @JSStaticFunction
            fun clear() {
                com.sungbin.gitkakaobot.util.api.AppData.clear()
            }

            @JvmStatic
            @JSStaticFunction
            fun remove(name: String) {
                com.sungbin.gitkakaobot.util.api.AppData.remove(name)
            }
        }
    }

    class Api : ScriptableObject() {
        override fun getClassName() = "Api"

        companion object {
            @JvmStatic
            @JSStaticFunction
            fun getContext() = context

            @JvmStatic
            @JSStaticFunction
            fun replyRoom(room: String, msg: String) =
                com.sungbin.gitkakaobot.util.api.Api.replyRoom(room, msg)

            @JvmStatic
            @JSStaticFunction
            fun replyRoomShowAll(room: String, msg1: String, msg2: String) =
                com.sungbin.gitkakaobot.util.api.Api.replyRoomShowAll(room, msg1, msg2)
        }
    }

    class Game : ScriptableObject() {
        override fun getClassName() = "Game"

        companion object {
            @JvmStatic
            @JSStaticFunction
            fun getRandomChosungQuiz() =
                com.sungbin.gitkakaobot.util.api.Game.chosungQuiz(ChosungType.getRandom())

            @JvmStatic
            @JSStaticFunction
            fun getChosungQuiz(type: Int) = com.sungbin.gitkakaobot.util.api.Game.chosungQuiz(type)
        }
    }

    class Device : ScriptableObject() {
        override fun getClassName() = "Device"

        companion object {
            @JvmStatic
            @JSStaticFunction
            fun getBattery() = com.sungbin.gitkakaobot.util.api.Device.battery

            @JvmStatic
            @JSStaticFunction
            fun getPhoneModel() = com.sungbin.gitkakaobot.util.api.Device.phoneModel

            @JvmStatic
            @JSStaticFunction
            fun getAndroidSDKVersion() = com.sungbin.gitkakaobot.util.api.Device.androidVersion

            @JvmStatic
            @JSStaticFunction
            fun getAndroidVersion() = com.sungbin.gitkakaobot.util.api.Device.androidVersion

            @JvmStatic
            @JSStaticFunction
            fun getIsCharging() = com.sungbin.gitkakaobot.util.api.Device.isCharging
        }
    }

    class Bridge : ScriptableObject() {
        override fun getClassName(): String {
            return "Bridge"
        }

        companion object {
            @JvmStatic
            @JSStaticFunction
            fun get(name: String) = scopes[name]
        }
    }

    class DataBase : ScriptableObject() {
        override fun getClassName(): String {
            return "DataBase"
        }

        companion object {
            @JvmStatic
            @JSStaticFunction
            fun read(name: String) = StorageUtils.read("$DATABASE/$name", null)

            @JvmStatic
            @JSStaticFunction
            fun save(name: String, content: String) {
                StorageUtils.save("$DATABASE/$name", content)
            }

            @JvmStatic
            @JSStaticFunction
            fun remove(name: String) {
                StorageUtils.delete("$DATABASE/$name")
            }
        }
    }

    class File : ScriptableObject() {
        override fun getClassName(): String {
            return "File"
        }

        companion object {
            @JvmStatic
            @JvmOverloads
            @JSStaticFunction
            fun read(path: String, _null: String?, autoInputSdcardPath: Boolean = false) =
                StorageUtils.read(path, _null, autoInputSdcardPath)

            @JvmStatic
            @JvmOverloads
            @JSStaticFunction
            fun save(path: String, content: String, autoInputSdcardPath: Boolean = false) =
                StorageUtils.save(path, content, autoInputSdcardPath)

            @JvmStatic
            @JvmOverloads
            @JSStaticFunction
            fun append(
                path: String,
                content: String,
                autoInputSdcardPath: Boolean = false
            ) = StorageUtils.save(
                path,
                "${StorageUtils.read(path, "")}$content",
                autoInputSdcardPath
            )

            @JvmStatic
            @JvmOverloads
            @JSStaticFunction
            fun delete(path: String, autoInputSdcardPath: Boolean = false) =
                StorageUtils.delete(path, autoInputSdcardPath)

            @JvmStatic
            @JvmOverloads
            @JSStaticFunction
            fun deleteAll(path: String, autoInputSdcardPath: Boolean = false) =
                StorageUtils.deleteAll(path, autoInputSdcardPath)
        }
    }

    class Black : ScriptableObject() {
        override fun getClassName(): String {
            return "Black"
        }

        companion object {
            @JvmStatic
            @JSStaticFunction
            fun getSender() = com.sungbin.gitkakaobot.util.api.Black.readSender()

            @JvmStatic
            @JSStaticFunction
            fun getRoom() = com.sungbin.gitkakaobot.util.api.Black.readRoom()

            @JvmStatic
            @JSStaticFunction
            fun addRoom(room: String) {
                com.sungbin.gitkakaobot.util.api.Black.addRoom(room)
            }

            @JvmStatic
            @JSStaticFunction
            fun addSender(sender: String) {
                com.sungbin.gitkakaobot.util.api.Black.addSender(sender)
            }

            @JvmStatic
            @JSStaticFunction
            fun removeRoom(room: String) {
                com.sungbin.gitkakaobot.util.api.Black.removeRoom(room)
            }

            @JvmStatic
            @JSStaticFunction
            fun removeSender(sender: String) {
                com.sungbin.gitkakaobot.util.api.Black.removeSender(sender)
            }
        }
    }

    class Utils : ScriptableObject() {
        override fun getClassName(): String {
            return "Utils"
        }

        companion object {
            @JvmStatic
            @JSStaticFunction
            fun makeToast(content: String) {
                UiUtil.toast(context, content)
            }

            @JvmStatic
            @JSStaticFunction
            fun delay(time: Int) {
                Thread.sleep((time * 1000).toLong())
            }

            @JvmStatic
            @JSStaticFunction
            fun makeNoti(title: String, content: String) {
                com.sungbin.gitkakaobot.util.api.Utils.makeNoti(title, content)
            }

            @JvmStatic
            @JvmOverloads
            @JSStaticFunction
            fun getHtml(link: String, fromJsoup: Boolean = true) = if (!fromJsoup) {
                com.sungbin.gitkakaobot.util.api.Api.getHtmlFromJava(link)
            } else com.sungbin.gitkakaobot.util.api.Api.getHtmlFromJsoup(link)

            @JvmStatic
            @JSStaticFunction
            fun post(address: String, postName: NativeArray, postData: NativeArray) = com.sungbin.gitkakaobot.util.api.Api.post(address, postName, postData)

            @JvmStatic
            @JSStaticFunction
            fun showAll() =  MessageListener.showAll

            @JvmStatic
            @JSStaticFunction
            fun makeVibration(time: Int) {
                com.sungbin.gitkakaobot.util.api.Utils.makeVibration(time)
            }

            @JvmStatic
            @JSStaticFunction
            fun copy(content: String) {
                com.sungbin.gitkakaobot.util.api.Utils.copy(content)
            }
        }
    }
}