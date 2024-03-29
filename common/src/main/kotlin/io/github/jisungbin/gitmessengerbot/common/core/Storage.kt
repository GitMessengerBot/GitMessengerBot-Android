/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Storage.kt] created by Ji Sungbin on 21. 8. 30. 오후 5:05
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.common.core

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object Storage {
    @Suppress("DEPRECATION")
    val sdcard: String = Environment.getExternalStorageDirectory().absolutePath

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.R)
    val isScoped = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

    private fun String.parsePath() = if (contains(sdcard)) this else "$sdcard/$this"

    fun write(path: String, content: String) {
        if (isScoped) return
        val file = File(path.parsePath())
        if (!file.exists()) {
            File(file.path.substringBeforeLast("/")).mkdirs()
            file.createNewFile()
        }
        FileOutputStream(file).use { it.write(content.toByteArray()) }
    }

    fun read(path: String, default: String?): String? {
        if (isScoped) return default
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
        if (isScoped) return
        val preContent = read(path, "")
        val newContent = "$preContent$prefix$appendContent"
        write(path, newContent)
    }

    fun delete(path: String) {
        if (isScoped) return
        File(path.parsePath()).delete()
    }

    fun deleteAll(path: String) {
        if (isScoped) return
        File(path.parsePath()).deleteRecursively()
    }

    fun fileList(path: String) =
        if (isScoped) emptyList() else File(path.parsePath()).listFiles()?.toList() ?: emptyList()

    @RequiresApi(Build.VERSION_CODES.R)
    fun isStorageManagerPermissionGranted() = Environment.isExternalStorageManager()

    @RequiresApi(Build.VERSION_CODES.R)
    fun requestStorageManagePermission(activity: Activity) {
        if (isScoped) return
        val intent = Intent(
            Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
            "package:${activity.packageName}".toUri()
        )
        activity.startActivity(intent)
    }
}
