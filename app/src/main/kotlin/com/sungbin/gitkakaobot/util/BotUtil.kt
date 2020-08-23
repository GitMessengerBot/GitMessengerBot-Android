package com.sungbin.gitkakaobot.util

import android.content.Context
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.model.BotItem
import com.sungbin.gitkakaobot.model.BotType
import com.sungbin.gitkakaobot.util.manager.PathManager.DEFAULT_CODE
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

    fun initBotList() {
        File("${StorageUtils.sdcard}/$JS").listFiles()?.map {
            val botJsonPath = "${it.path}/data.json"
            val botJson = StorageUtils.read(botJsonPath, null, false)
            botJson?.let { json ->
                val botJsonObject = JSONObject(json)
                jsBotItems.add(
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

    fun createNewBot(context: Context, bot: BotItem) {
        val defaultCode = DataUtil.read(
            context,
            DEFAULT_CODE,
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

}