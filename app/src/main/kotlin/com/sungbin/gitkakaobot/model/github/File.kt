package com.sungbin.gitkakaobot.model.github

class File internal constructor(
    val message: String,
    val content: String,
    val sha: String,
    val branch: String
)