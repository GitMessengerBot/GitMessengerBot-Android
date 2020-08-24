package com.sungbin.gitkakaobot.util

import android.content.Context
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.model.BotItem
import com.sungbin.gitkakaobot.model.BotType
import com.sungbin.gitkakaobot.util.manager.PathManager
import com.sungbin.gitkakaobot.util.manager.PathManager.JS
import com.sungbin.gitkakaobot.util.manager.PathManager.SIMPLE
import com.sungbin.sungbintool.StorageUtils
import org.json.JSONObject
import java.io.File

object BotUtil {

    val jsBotItems = ArrayList<BotItem>()
    val simpleBotItems = ArrayList<BotItem>()

    private fun getBotPath(bot: BotItem) =
        "${if (bot.type == BotType.JS) JS else SIMPLE}/${bot.uuid}"

    fun getLastIndex(type: Int, botList: ArrayList<BotItem>): Int {
        var maxIndex = 0
        botList.map {
            if (it.index > maxIndex) maxIndex = it.index
        }
        return maxIndex
    }

    fun getBotCode(bot: BotItem) = StorageUtils.read(
        "${getBotPath(bot)}/index.${if (bot.type == BotType.JS) "js" else "srd"}",
        "",
        true
    ).toString()

    fun saveBotCode(bot: BotItem, code: String) = StorageUtils.save(
        "${getBotPath(bot)}/index.${if (bot.type == BotType.JS) "js" else "srd"}",
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
        val defaultCode = DataUtil.read(
            context,
            PathManager.DEFAULT_CODE,
            context.getString(R.string.default_sourcecode)
        ).toString()
        val path = getBotPath(bot)
        StorageUtils.createFolder(path, true)
        StorageUtils.createFile("$path/index.${if (bot.type == BotType.JS) "js" else "srd"}", true)
        StorageUtils.createFile("$path/data.json", true)
        StorageUtils.save(
            "$path/data.json",
            bot.toString(),
            true
        )
        StorageUtils.save(
            "$path/index.${if (bot.type == BotType.JS) "js" else "srd"}",
            defaultCode,
            true
        )
    }

    fun createBotItem(botJsonObject: JSONObject) = BotItem(
        botJsonObject.getString("name"),
        botJsonObject.getBoolean("isCompiled"),
        botJsonObject.getBoolean("power"),
        botJsonObject.getInt("type"),
        botJsonObject.getString("lastRunTime"),
        botJsonObject.getInt("index"),
        botJsonObject.getString("uuid")
    )

    fun initBotList() {
        jsBotItems.clear()
        simpleBotItems.clear()

        File("${StorageUtils.sdcard}/$JS").listFiles()?.map {
            val botJsonPath = "${it.path}/data.json"
            val botJson = StorageUtils.read(botJsonPath, null, false)
            botJson?.let { json ->
                jsBotItems.add(
                    createBotItem(JSONObject(json))
                )
            }
        }
        File("${StorageUtils.sdcard}/$SIMPLE").listFiles()?.map {
            val botJsonPath = "${it.path}/data.json"
            val botJson = StorageUtils.read(botJsonPath, null, false)
            botJson?.let { json ->
                val botJsonObject = JSONObject(json)
                simpleBotItems.add(
                    BotItem(
                        botJsonObject.getString("name"),
                        botJsonObject.getBoolean("isCompiled"),
                        botJsonObject.getBoolean("power"),
                        botJsonObject.getInt("type"),
                        botJsonObject.getString("lastRunTime"),
                        botJsonObject.getInt("index"),
                        botJsonObject.getString("uuid")
                    )
                )
            }
        }
    }

}