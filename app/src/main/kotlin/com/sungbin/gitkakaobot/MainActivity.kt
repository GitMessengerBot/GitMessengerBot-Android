package com.sungbin.gitkakaobot

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.appcompat.app.AppCompatActivity
import com.sungbin.gitkakaobot.listener.KakaoTalkListener.Companion.compileJavaScript
import com.sungbin.gitkakaobot.util.DataUtil
import com.sungbin.gitkakaobot.util.UiUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
        startActivity(intent)

        sw_power.isChecked = DataUtil.read(applicationContext, "power", "false").toBoolean()
        sw_power.setOnCheckedChangeListener { _, isChecked ->
            DataUtil.save(applicationContext, "power", isChecked.toString())
        }

        et_input.text = SpannableStringBuilder(
            DataUtil.read(
                applicationContext,
                "sourcecode",
                getString(R.string.default_sourcecode)
            )
        )

        iv_save.setOnClickListener {
            DataUtil.save(applicationContext, "sourcecode", et_input.text.toString())
            UiUtil.toast(applicationContext, "저장되었습니다.")
        }

        btn_reload.setOnClickListener {
            UiUtil.snackbar(
                it,
                compileJavaScript("script", et_input.text.toString())
            )
        }
    }
}