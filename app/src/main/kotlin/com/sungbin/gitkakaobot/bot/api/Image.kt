package com.sungbin.gitkakaobot.bot.api

import com.sungbin.gitkakaobot.util.manager.PictureManager
import com.sungbin.gitkakaobot.util.toBase64String


/**
 * Created by SungBin on 2020-12-28.
 */

class Image {

    fun getLastImage() = PictureManager.getKakaoTalkLastPictureBase64()
    fun getProfileImage(sender: String) = PictureManager.profileImage[sender].toBase64String()

}