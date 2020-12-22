package com.sungbin.gitkakaobot.bot.rhino

import android.graphics.Bitmap
import com.sungbin.gitkakaobot.util.toBase64String

class ImageDB(private val profileImage: Bitmap?) {
    fun getProfileImage() = profileImage.toBase64String()
    fun getLastImage() = PicturePathManager.getLastPicture()
}