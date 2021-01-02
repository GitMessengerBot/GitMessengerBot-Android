package com.sungbin.gitkakaobot.util

import android.content.Context
import com.sungbin.androidutils.util.StorageUtil
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.model.Bot
import com.sungbin.gitkakaobot.model.BotType
import com.sungbin.gitkakaobot.util.manager.PathManager
import org.json.JSONObject
import java.io.File

object BotUtil {

    val showAll = "\u200b".repeat(500)
    private lateinit var context: Context
    val botItems = ArrayList<Bot>()

    fun init(context: Context) {
        this.context = context
    }

    private val defaultCode: String
        get() = DataUtil.readData(
            context,
            PathManager.DEFAULT_CODE,
            context.getString(R.string.bot_default_sourcecode)
        ).toString()

    private fun getBotPath(bot: Bot) = "${
        when (bot.type) {
            BotType.SIMPLE -> PathManager.SIMPLE
            else -> PathManager.JS
        }
    }/${bot.name}-${bot.uuid.substring(0..5)}"

    fun getLastIndex(botList: ArrayList<Bot>?): Int {
        var maxIndex = 0
        botList?.map {
            if (it.index > maxIndex) maxIndex = it.index
        }
        return maxIndex
    }

    private fun getBotSuffix(bot: Bot) = if (bot.type == BotType.SIMPLE) "srd" else "js"

    fun updateBotPower(bot: Bot, power: Boolean) {
        bot.power = power
        updateBotData(bot)
    }

    fun getBotCode(bot: Bot) = StorageUtil.read(
        "${getBotPath(bot)}/index.${getBotSuffix(bot)}", defaultCode
    ).toString()

    fun saveBotCode(bot: Bot, code: String) = StorageUtil.save(
        "${getBotPath(bot)}/index.${getBotSuffix(bot)}", code
    ).toString()

    fun updateBotData(bot: Bot) =
        StorageUtil.save("${getBotPath(bot)}/data.json", bot.toString())

    fun changeBotIndex(bot: Bot, newPos: Int) {
        val botJsonPath = "${getBotPath(bot)}/data.json"
        val botJson = JSONObject(StorageUtil.read(botJsonPath, "")!!)
        botJson.put("index", newPos)
        StorageUtil.save(botJsonPath, botJson.toString())
    }

    fun createNewBot(bot: Bot) {
        val path = getBotPath(bot)
        StorageUtil.createFolder(path)
        StorageUtil.createFile(
            "$path/index.${getBotSuffix(bot)}"
        )
        StorageUtil.createFile("$path/data.json")
        StorageUtil.save(
            "$path/data.json",
            bot.toString()
        )
        StorageUtil.save(
            "$path/index.${getBotSuffix(bot)}",
            defaultCode
        )
    }

    fun createBotItem(botJsonObject: JSONObject) = Bot(
        botJsonObject.getString("name"),
        botJsonObject.getBoolean("power"),
        botJsonObject.getInt("type"),
        botJsonObject.getString("lastRunTime"),
        botJsonObject.getInt("index"),
        botJsonObject.getString("uuid")
    )

    fun initBotList() {
        botItems.clear()

        File("${StorageUtil.sdcard}/${PathManager.JS}").listFiles()?.map {
            val botJsonPath = "${it.path}/data.json"
            val botJson = StorageUtil.read(botJsonPath, null)
            botJson?.let { json ->
                botItems.add(createBotItem(JSONObject(json)))
            }
        }
        File("${StorageUtil.sdcard}/${PathManager.SIMPLE}").listFiles()?.map {
            val botJsonPath = "${it.path}/data.json"
            val botJson = StorageUtil.read(botJsonPath, null)
            botJson?.let { json ->
                botItems.add(createBotItem(JSONObject(json)))
            }
        }
    }

}