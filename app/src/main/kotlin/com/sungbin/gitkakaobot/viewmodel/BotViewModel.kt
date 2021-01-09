package com.sungbin.gitkakaobot.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sungbin.gitkakaobot.model.Bot
import com.sungbin.gitkakaobot.util.BotUtil


/**
 * Created by SungBin on 2021-01-09.
 */

class BotViewModel : ViewModel() {
    lateinit var onBackPressedAction: () -> Unit

    val botList: MutableLiveData<ArrayList<Bot>> = MutableLiveData()

    fun initBotList() {
        if (botList.value?.isEmpty() != false) botList.value = BotUtil.botItems
    }
}