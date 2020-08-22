package com.sungbin.gitkakaobot

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.appcompat.app.AppCompatActivity
import com.sungbin.gitkakaobot.util.DataUtils
import com.sungbin.gitkakaobot.util.RhinoUtils
import com.sungbin.gitkakaobot.util.UiUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sw_power.isChecked = DataUtils.read(applicationContext, "power", "false").toBoolean()
        sw_power.setOnCheckedChangeListener { _, isChecked ->
            DataUtils.save(applicationContext, "power", isChecked.toString())
        }

        et_input.text = SpannableStringBuilder(
            DataUtils.read(
                applicationContext,
                "sourcecode",
                getString(R.string.default_sourcecode)
            )
        )
        iv_save.setOnClickListener {
            DataUtils.save(applicationContext, "sourcecode", et_input.text.toString())
            UiUtils.toast(applicationContext, "저장되었습니다.")
        }

        btn_reload.setOnClickListener {
            val sourcecode = et_input.text.toString()
            val result = RhinoUtils.run(sourcecode)
            UiUtils.toast(applicationContext, result)
        }
    }
}