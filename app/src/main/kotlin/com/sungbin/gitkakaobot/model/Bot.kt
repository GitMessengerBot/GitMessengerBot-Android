package com.sungbin.gitkakaobot.model

import org.json.JSONObject


/**
 * Created by SungBin on 2020-08-23.
 */

data class Bot(
    var name: String,
    var power: Boolean,
    var type: Int,
    var lastRunTime: String,
    var index: Int,
    var uuid: String,
) {
    override fun toString() = JSONObject().apply {
        put("name", name)
        put("power", power)
        put("type", type)
        put("lastRunTime", lastRunTime)
        put("index", index)
        put("uuid", uuid)
    }.toString()
}