package com.sungbin.gitkakaobot.model


/**
 * Created by SungBin on 2020-08-23.
 */

data class BotItem(
    val name: String,
    val isCompiled: Boolean,
    val power: Boolean,
    val type: Int,
    val lastRunTime: String,
    val index: Int
)