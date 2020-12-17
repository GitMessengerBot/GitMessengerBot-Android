package com.sungbin.gitkakaobot.ui.fragment.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sungbin.androidutils.util.Logger
import com.sungbin.gitkakaobot.model.Bot
import com.sungbin.gitkakaobot.util.BotUtil

class BotViewModel : ViewModel() {

    init {
        Logger.w("vm 초기화됨")
    }

    val botList: MutableLiveData<ArrayList<Bot>> = MutableLiveData()

    fun initBotList() {
        if (botList.value?.isEmpty() != false) botList.value = BotUtil.botItems
    }

}