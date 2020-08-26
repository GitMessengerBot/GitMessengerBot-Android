package com.sungbin.gitkakaobot.util

import android.content.Context
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.model.BotItem
import com.sungbin.gitkakaobot.model.BotType
import com.sungbin.gitkakaobot.util.manager.PathManager.DEBUG
import com.sungbin.gitkakaobot.util.manager.PathManager.JS
import com.sungbin.gitkakaobot.util.manager.PathManager.SIMPLE
import com.sungbin.sungbintool.StorageUtils
import com.sungbin.sungbintool.Utils
import org.json.JSONObject
import org.mozilla.javascript.Function
import java.io.File

object BotUtil {

    object Event {
        const val ERROR = -1
        const val MESSAGE = 0
        const val COMPILE_START = 1
        const val COMPILE_END = 2
        const val DEBUG = 999
    }

    val botItems = ArrayList<BotItem>()
    val functions = HashMap<String, HashMap<Int, Function>>()

    private fun getBotPath(bot: BotItem) = when (bot.type) {
        BotType.DEBUG -> DEBUG
        BotType.JS -> JS
        else -> SIMPLE
    }

    fun getLastIndex(botList: ArrayList<BotItem>): Int {
        var maxIndex = 0
        botList.map {
            if (it.index > maxIndex) maxIndex = it.index
        }
        return maxIndex
    }

    fun getDebugBot(context: Context, name: String, isRuningRoom: Boolean = false): BotItem {
        val bot = BotItem(
            name,
            false,
            false,
            BotType.DEBUG,
            -1,
            "없음",
            -1,
            if (isRuningRoom) "-1" else Utils.makeRandomUUID()
        )
        createNewBot(context, bot)
        return bot
    }

    fun getBotCode(bot: BotItem) = StorageUtils.read(
        "${getBotPath(bot)}/index.${if (bot.type == BotType.JS || bot.type == BotType.DEBUG) "js" else "srd"}",
        "",
        true
    ).toString()

    fun saveBotCode(bot: BotItem, code: String) = StorageUtils.save(
        "${getBotPath(bot)}/index.${if (bot.type == BotType.JS || bot.type == BotType.DEBUG) "js" else "srd"}",
        code,
        true
    ).toString()

    fun updateBotData(bot: BotItem) =
        StorageUtils.save("${getBotPath(bot)}/data.json", bot.toString(), true)

    fun changeBotIndex(bot: BotItem, newPos: Int) {
        val botJsonPath = "${getBotPath(bot)}/data.json"
        val botJson = JSONObject(StorageUtils.read(botJsonPath, "", true)!!)
        botJson.put("index", newPos)
        StorageUtils.save(botJsonPath, botJson.toString(), true)
    }

    fun createNewBot(context: Context, bot: BotItem) {
        /*val defaultCode = DataUtil.read(
            context,
            PathManager.DEFAULT_CODE,
            context.getString(R.string.default_sourcecode)
        ).toString()*/
        val defaultCode = context.getString(R.string.default_sourcecode)
        val path = getBotPath(bot)
        botItems.add(bot)
        StorageUtils.createFolder(path, true)
        StorageUtils.createFile(
            "$path/index.${if (bot.type == BotType.JS || bot.type == BotType.DEBUG) "js" else "srd"}",
            true
        )
        StorageUtils.createFile("$path/data.json", true)
        StorageUtils.save(
            "$path/data.json",
            bot.toString(),
            true
        )
        StorageUtils.save(
            "$path/index.${if (bot.type == BotType.JS || bot.type == BotType.DEBUG) "js" else "srd"}",
            defaultCode,
            true
        )
    }

    fun createBotItem(botJsonObject: JSONObject) = BotItem(
        botJsonObject.getString("name"),
        botJsonObject.getBoolean("isCompiled"),
        botJsonObject.getBoolean("power"),
        botJsonObject.getInt("type"),
        botJsonObject.getInt("optimization"),
        botJsonObject.getString("lastRunTime"),
        botJsonObject.getInt("index"),
        botJsonObject.getString("uuid")
    )

    fun initBotList() {
        botItems.clear()

        File("${StorageUtils.sdcard}/$JS").listFiles()?.map {
            val botJsonPath = "${it.path}/data.json"
            val botJson = StorageUtils.read(botJsonPath, null, false)
            botJson?.let { json ->
                botItems.add(createBotItem(JSONObject(json)))
            }
        }
        File("${StorageUtils.sdcard}/$SIMPLE").listFiles()?.map {
            val botJsonPath = "${it.path}/data.json"
            val botJson = StorageUtils.read(botJsonPath, null, false)
            botJson?.let { json ->
                val botJsonObject = JSONObject(json)
                botItems.add(createBotItem(JSONObject(json)))
            }
        }
    }

}