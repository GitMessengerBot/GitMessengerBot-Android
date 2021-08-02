package io.github.jisungbin.gitmessengerbot.theme

import android.os.Build
import android.view.View
import android.view.Window
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb

// https://gist.github.com/chrisbanes/ab31bf7b67b77948157687af010f0667#file-systemui-kt
@Suppress("DEPRECATION")
class SystemUiController(private val window: Window) {
    fun setStatusBarColor(
        color: Color,
        darkIcons: Boolean = color.luminance() > 0.5f
    ) {
        window.statusBarColor = color.toArgb()
        if (darkIcons) {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            window.decorView.systemUiVisibility = window.decorView.systemUiVisibility and
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }

    fun setNavigationBarColor(
        color: Color,
        darkIcons: Boolean = color.luminance() > 0.5f
    ) {
        window.navigationBarColor = color.toArgb()
        if (Build.VERSION.SDK_INT >= 26) {
            if (darkIcons) {
                window.decorView.systemUiVisibility = window.decorView.systemUiVisibility or
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            } else {
                window.decorView.systemUiVisibility = window.decorView.systemUiVisibility and
                    View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
            }
        }
    }

    fun setSystemBarsColor(
        color: Color,
        isDarkIcon: Boolean = color.luminance() > 0.5f
    ) {
        setStatusBarColor(color, isDarkIcon)
        setNavigationBarColor(color, isDarkIcon)
    }
}
