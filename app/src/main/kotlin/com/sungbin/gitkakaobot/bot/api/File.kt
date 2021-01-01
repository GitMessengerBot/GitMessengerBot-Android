package com.sungbin.gitkakaobot.bot.api

import com.sungbin.androidutils.util.StorageUtil

class File {

    fun save(path: String, content: String) = StorageUtil.save(path, content)
    fun read(path: String, _null: String? = null) = StorageUtil.read(path, _null)

}