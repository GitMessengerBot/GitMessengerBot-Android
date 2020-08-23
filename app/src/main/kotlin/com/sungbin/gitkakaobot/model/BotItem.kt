package com.sungbin.gitkakaobot.model

import org.json.JSONObject





/**
 * Created by SungBin on 2020-08-23.
 */

data class BotItem(
    val name: String,
    val isCompiled: Boolean,
    val power: Boolean,
    val type: Int,
    val lastRunTime: String,
    val index: Int,
    val uuid: String
) {
    override fun toString(): String {
        return JSONObject().run {
            put("name", name)
            put("isCompiled", isCompiled)
            put("power", power)
            put("type", type)
            put("lastRunTime", lastRunTime)
            put("index", index)
            put("uuid", uuid)
        }.toString()
    }
}