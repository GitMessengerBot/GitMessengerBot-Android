package com.sungbin.gitkakaobot.util.api

import com.github.kimkevin.hangulparser.HangulParser
import com.sungbin.gitkakaobot.util.api.game.chosung.ChosungData
import com.sungbin.gitkakaobot.util.api.game.chosung.ChosungType
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by SungBin on 2020-05-14.
 */

object Game {

    fun chosungQuiz(type: Int): ArrayList<Any> {
        val array = ArrayList<Any>()
        val subject = ChosungType.getName(type)
        val words = ChosungData.getData(type).split("\n")
        val index = Random().nextInt(words.size - 1)
        val word = words[index]
        val chosungs = ArrayList<String>()
        for (element in word) {
            chosungs.add(HangulParser.disassemble(element)[0])
        }
        array.run {
            add(subject)
            add(word)
            add(chosungs)
        }
        return array
    }

}