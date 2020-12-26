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
        val byteArrayOutputStream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()
        Base64.encodeToString(bytes, Base64.NO_WRAP)
    }
}

fun String.toBase64(): String {
    val bytes = this.toByteArray(Charsets.UTF_8)
    return Base64.encodeToString(bytes, Base64.NO_WRAP)
}

fun String.fromBase64() = String(Base64.decode(this, Base64.NO_WRAP), Charsets.UTF_8)