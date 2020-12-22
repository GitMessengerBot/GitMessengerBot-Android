package com.sungbin.gitkakaobot.util

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream


/**
 * Created by SungBin on 2020-12-22.
 */

fun Bitmap?.toBase64String(): String? {
    return if (this == null) null
    else {
        val baos = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val bImage = baos.toByteArray()
        Base64.encodeToString(bImage, 0)
    }
}