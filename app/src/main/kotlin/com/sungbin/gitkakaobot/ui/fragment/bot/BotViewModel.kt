package com.sungbin.gitkakaobot.ui.fragment.bot

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sungbin.gitkakaobot.model.BotItem
import com.sungbin.gitkakaobot.util.BotUtil

class BotViewModel : ViewModel() {

    val botList: MutableLiveData<ArrayList<BotItem>> = MutableLiveData()

    fun initBotList() {
        botList.value = BotUtil.botItems
    }

}