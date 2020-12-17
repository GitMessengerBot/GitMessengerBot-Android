package com.sungbin.gitkakaobot.util.api.game.chosung

import java.util.*

object ChosungType {
    const val FOOD = 0
    const val ARTIST = 1
    const val COUNTRY = 2
    const val LOCATION = 3
    const val MATH = 4
    const val SPORT = 5
    const val BRAND = 6
    const val ELEMENT = 7
    const val POCKETMON = 8
    const val CHEMISTRY = 9
    const val WORDS = 10

    fun getRandom(): Int {
        return Random().nextInt(11)
    }

    fun getName(type: Int): String {
        return when (type) {
            0 -> "간식"
            1 -> "국내 가수"
            2 -> "국가"
            3 -> "도시"
            4 -> "수학"
            5 -> "스포츠"
            6 -> "브랜드"
            7 -> "원소"
            8 -> "포켓몬"
            9 -> "화학"
            10 -> "단어"
            else -> "음식"
        }
    }
}