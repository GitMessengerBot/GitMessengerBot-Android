package com.sungbin.gitkakaobot.bot.v8

import com.sungbin.androidutils.util.StorageUtil

class File {

    fun save(path: String, content: String) = StorageUtil.save(path, content)

    @JvmOverloads
    fun read(path: String, _null: String? = null) = StorageUtil.read(path, _null)

}