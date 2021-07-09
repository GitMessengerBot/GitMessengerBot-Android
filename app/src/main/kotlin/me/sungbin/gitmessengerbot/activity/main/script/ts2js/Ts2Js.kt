package me.sungbin.gitmessengerbot.activity.main.script.ts2js

import com.google.gson.annotations.SerializedName

data class Ts2Js(
    @field:SerializedName("diagnostics")
    val diagnostics: List<Any>,

    @field:SerializedName("outputText")
    val tsCode: String
)
