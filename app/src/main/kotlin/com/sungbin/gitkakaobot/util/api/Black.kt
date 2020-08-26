package com.sungbin.gitkakaobot.util.api

import android.content.Context
import com.sungbin.sungbintool.DataUtils.readData
import com.sungbin.sungbintool.DataUtils.saveData

object Black {
    private lateinit var context: Context
    
    fun init(context: Context) {
        this.context = context
    }

    fun addSender(sender: String) {
        val preSenderList: String = readData(context, "SenderBlackList", "")
        val newSenderList = preSenderList + "\n" + sender
        saveData(context, "SenderBlackList", newSenderList)
    }

    fun removeSender(sender: String) {
        val preSenderList: String = readData(context, "SenderBlackList", "")
        val newSenderList = preSenderList.replace("\n" + sender, "")
        saveData(context, "SenderBlackList", newSenderList)
    }

    fun readSender() = readData(context, "SenderBlackList", "")

    fun addRoom(sender: String) {
        val preSenderList: String = readData(context, "RoomBlackList", "")
        val newSenderList = preSenderList + "\n" + sender
        saveData(context, "RoomBlackList", newSenderList)
    }

    fun removeRoom(sender: String) {
        val preSenderList: String = readData(context, "RoomBlackList", "")
        val newSenderList = preSenderList.replace("\n" + sender, "")
        saveData(context, "RoomBlackList", newSenderList)
    }

    fun readRoom() = readData(context, "RoomBlackList", "")
}