package com.sungbin.gitkakaobot.util.api

import android.content.Context
import android.os.StrictMode
import com.sungbin.gitkakaobot.listener.MessageListener
import com.sungbin.gitkakaobot.listener.MessageListener.Companion.showAll
import com.sungbin.gitkakaobot.util.manager.StackManager.sessions
import com.sungbin.sungbintool.DataUtils
import org.jsoup.Jsoup
import org.mozilla.javascript.NativeArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

object Api {
    private lateinit var context: Context
    private var userAgent =
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36"

    fun init(context: Context) {
        this.context = context
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    fun setUserAgent(agent: String) {
        userAgent = agent
    }

    fun getHtmlFromJava(address: String): String? {
        try {
            val url = URL(address)
            val con = url.openConnection()
            if (con != null) {
                con.connectTimeout =
                    DataUtils.readData(context, "HtmlLimitTime", "5").toInt() * 1000
                con.useCaches = false
                val isr = InputStreamReader(con.getInputStream())
                val br = BufferedReader(isr)
                var str = br.readLine()
                var line: String? = ""
                while ({ line = br.readLine(); line }() != null) {
                    str += "\n" + line
                }
                br.close()
                isr.close()
                return str
            }
            return null
        } catch (e: Exception) {
            return e.toString()
        }
    }

    @JvmOverloads
    fun getHtmlFromJsoup(address: String, userAgent: String = this.userAgent) = Jsoup
        .connect(address)
        .userAgent(userAgent)
        .timeout(DataUtils.readData(context, "HtmlLimitTime", "5").toInt() * 1000)
        .get().toString()

    fun replyRoom(room: String, msg: String) {
        sessions[room]?.let {
            MessageListener.replyToSession(it, msg)
        }
    }

    fun replyRoomShowAll(
        room: String,
        msg1: String,
        msg2: String
    ) {
        sessions[room]?.let {
            MessageListener.replyToSession(
                it,
                msg1 + showAll + msg2
            )
        }
    }

    data class PostResult(val success: Boolean, val exception: Exception?)

    fun post(
        address: String,
        postName: NativeArray,
        postData: NativeArray
    ): PostResult {
        return if (postName.size != postData.size) PostResult(
            false,
            Exception("postName과 postData의 크기가 같아야 합니다.")
        )
        else {
            Thread {
                try {
                    val builder =
                        Jsoup.connect(address).ignoreContentType(true).ignoreContentType(true)
                    for (i in 0 until postName.size) {
                        builder.data(postName[i].toString(), postData[i].toString())
                    }
                    builder.post()
                } catch (e: Exception) {
                    PostResult(false, e)
                }
            }.start()
            PostResult(true, null)
        }
    }
}