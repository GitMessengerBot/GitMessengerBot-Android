/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Storage.kt] created by Ji Sungbin on 21. 6. 14. 오후 7:06.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.util

import android.content.Context
import android.os.Build
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object Storage {
    val isScoped = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    private fun String.toFile(context: Context) =
        File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), this)

    fun write(context: Context, name: String, content: String) {
        FileOutputStream(name.toFile(context)).use { it.write(content.toByteArray()) }
    }

    fun read(context: Context, name: String, default: String?): String? {
        val file = name.toFile(context)
        return if (file.exists()) {
            FileInputStream(file).bufferedReader().use { it.readText() }
        } else {
            default
        }
    }

    fun append(context: Context, name: String, prefix: String, appendContent: String) {
        val preContent = read(context, name, "")
        val newContent = "$preContent$prefix$appendContent"
        write(context, name, newContent)
    }
}
