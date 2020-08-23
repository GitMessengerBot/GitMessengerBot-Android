package com.sungbin.gitkakaobot.ui.fragment.bot

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sungbin.gitkakaobot.model.BotItem
import com.sungbin.gitkakaobot.util.BotUtil

class BotViewModel : ViewModel() {

    val jsBotList: MutableLiveData<List<BotItem>> = MutableLiveData()
    val simpleBotList: MutableLiveData<List<BotItem>> = MutableLiveData()

    fun initBotList() {
        jsBotList.value = BotUtil.jsBotItems
        simpleBotList.value = BotUtil.simpleBotItems
    }

}