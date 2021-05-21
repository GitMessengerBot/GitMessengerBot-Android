/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [StorageUtil.kt] created by Ji Sungbin on 21. 5. 21. 오후 4:40.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.util

import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

@Suppress("DEPRECATION")
object StorageUtil {
    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    val sdcard = Environment.getExternalStorageDirectory().absolutePath!!

    private fun String.parsePath() = if (contains(sdcard)) this else "$sdcard/$this"

    fun getSize(size: Long): String {
        if (size <= 0) return "0"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (log10(size.toDouble()) / log10(1000.0)).toInt()
        return DecimalFormat("#,##0.#").format(
            size / 1000.0.pow(digitGroups.toDouble())
        ).toString() + " " + units[digitGroups]
    }

    fun getFileSize(file: File) = getSize(file.length())

    fun createFolder(path: String) = File(path.parsePath()).mkdirs()

    fun createFile(path: String) = File(path.parsePath()).createNewFile()

    fun read(path: String, _null: String?): String? {
        return try {
            val file = File(path.parsePath())
            if (!file.exists()) return _null
            val fis = FileInputStream(file)
            val isr = InputStreamReader(fis)
            isr.buffered(1024 * 1024).use { it.readText() }
        } catch (ignored: Exception) {
            _null
        }
    }

    fun save(path: String, content: String): Boolean {
        return try {
            val file = File(path.parsePath())
            val fos = FileOutputStream(file)
            fos.write(content.toByteArray())
            fos.close()
            true
        } catch (ignored: Exception) {
            false
        }
    }

    fun delete(path: String) = File(path.parsePath()).delete()

    fun deleteAll(path: String): Boolean {
        return try {
            val dir = File(path.parsePath())
            if (dir.exists() && dir.listFiles() != null) {
                for (childFile in dir.listFiles()!!) {
                    if (childFile.isDirectory) {
                        deleteAll(childFile.absolutePath)
                    } else {
                        childFile.delete()
                    }
                }
                dir.delete()
            }
            true
        } catch (ignored: Exception) {
            false
        }
    }
}
