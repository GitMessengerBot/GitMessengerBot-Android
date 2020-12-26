package com.sungbin.gitkakaobot.ui.activity

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.ScrollingMovementMethod
import android.text.style.StyleSpan
import androidx.appcompat.app.AppCompatActivity
import com.sungbin.gitkakaobot.databinding.ActivityExceptionBinding


/**
 * Created by SungBin on 2020-08-31.
 */

class ExceptionActivity : AppCompatActivity() {

    private val binding by lazy { ActivityExceptionBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val message = intent.getStringExtra("message") ?: "NullPointerException"
        binding.tvExcept.apply {
            val ssb = SpannableStringBuilder(message)
            ssb.setSpan(
                StyleSpan(Typeface.ITALIC),
                message.lastIndexOf("#"),
                message.lastIndex + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            text = ssb
            movementMethod = ScrollingMovementMethod()
        }

        binding.lavException.setOnClickListener {
            binding.lavException.playAnimation()
        }
    }

}