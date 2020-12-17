package com.sungbin.gitkakaobot.ui.dialog

import android.app.Activity
import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.ScrollingMovementMethod
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.sungbin.androidutils.extensions.plusAssign
import com.sungbin.gitkakaobot.R
import com.sungbin.gitkakaobot.databinding.LayoutLoadingDialogBinding


class LoadingDialog(private val activity: Activity) {

    private lateinit var alert: AlertDialog
    private val layout by lazy { LayoutLoadingDialogBinding.inflate(LayoutInflater.from(activity)) }

    fun show() {
        if (!::alert.isInitialized) {
            val dialog = AlertDialog.Builder(activity)
            dialog.setView(layout.root)
            dialog.setCancelable(false)

            alert = dialog.create()
            alert.window?.setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        activity,
                        android.R.color.transparent
                    )
                )
            )
        }

        if (alert.isShowing) alert.cancel()
        alert.show()
    }

    fun updateTitle(title: String) {
        layout.tvLoading.text = title
        layout.root.invalidate()
    }

    fun setCustomState(
        lottie: Int,
        message: String,
        canDismiss: Boolean,
        dismissListener: () -> Unit
    ) {
        layout.lavLoad.setAnimation(lottie)
        layout.lavLoad.playAnimation()
        layout.tvLoading += message
        layout.tvLoading.movementMethod = ScrollingMovementMethod()
        alert.setCancelable(canDismiss)
        alert.setOnDismissListener { dismissListener() }
        layout.root.invalidate()
    }

    fun setError(throwable: Throwable) {
        throwable.printStackTrace()
        layout.lavLoad.run {
            setAnimation(R.raw.error)
            playAnimation()
        }
        layout.tvLoading.run {
            val message =
                "서버 요청 중 오류가 발생했습니다!\n\n${throwable.message} #${throwable.stackTrace[0].lineNumber}"
            val ssb = SpannableStringBuilder(message)
            ssb.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorLightRed)),
                message.lastIndexOf("\n"),
                message.lastIndex + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            text = ssb
            movementMethod = ScrollingMovementMethod()
        }
        alert.setCancelable(true)
        layout.root.invalidate()
    }

    fun close() {
        if (::alert.isInitialized) alert.cancel()
    }
}