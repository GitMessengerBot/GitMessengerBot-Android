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
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object Storage {
    @Suppress("DEPRECATION")
    private val sdcard = Environment.getExternalStorageDirectory().absolutePath

    val isScoped = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    private fun String.parsePath() = if (contains(sdcard)) this else "$sdcard/$this"

    fun write(path: String, content: String) {
        val file = File(path.parsePath())
        if (!file.exists()) {
            File(file.path.substringBeforeLast("/")).mkdirs()
            file.createNewFile()
        }
        FileOutputStream(file).use { it.write(content.toByteArray()) }
    }

    fun read(path: String, default: String?): String? {
        return try {
            val file = File(path.parsePath())
            if (file.exists()) {
                FileInputStream(file).bufferedReader().use { it.readText() }
            } else {
                default
            }
        } catch (ignored: Exception) {
            default
        }
    }

    fun append(path: String, prefix: String, appendContent: String) {
        val preContent = read(path, "")
        val newContent = "$preContent$prefix$appendContent"
        write(path, newContent)
    }

    fun remove(path: String) {
        File(path.parsePath()).delete()
    }

    fun fileList(path: String) = File(path.parsePath()).listFiles()?.toList() ?: emptyList()

    @RequiresApi(Build.VERSION_CODES.R)
    fun requestStorageManagePermission(context: Context) {
        val uri = Uri.parse("package:" + context.packageName)
        context.startActivity(
            Intent(
                Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                uri
            )
        )
    }
}
