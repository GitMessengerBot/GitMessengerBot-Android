package com.sungbin.gitkakaobot.bot

import android.graphics.Bitmap
import android.util.Base64.encodeToString
import java.io.ByteArrayOutputStream

class ImageDB(private val bitmap: Bitmap?) {
    fun getProfileImage(): String? {
        if (bitmap == null) return null
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val bImage = baos.toByteArray()
        return encodeToString(bImage, 0)
    }

    fun getLastImage() = PicturePathManager.getLastPicture()
}