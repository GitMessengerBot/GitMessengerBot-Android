package com.sungbin.gitkakaobot.util.api.game.wordchain

import com.sungbin.sungbintool.Utils

object Word {
    val LIST = Utils.getHtml("https://raw.githubusercontent.com/sungbin5304/beautiful-text-message/master/pyogukWords.txt").toString()
}