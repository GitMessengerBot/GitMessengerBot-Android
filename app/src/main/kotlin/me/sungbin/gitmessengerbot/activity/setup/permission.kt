/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [permission.kt] created by Ji Sungbin on 21. 7. 8. 오후 11:37.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.setup

import androidx.annotation.DrawableRes
import androidx.compose.ui.unit.Dp

object PermissionType {
    const val NotificationRead = "notification-read"
    const val Wear = "android-wear"
    const val ScopedStorage = "scoped"
}

data class Permission(
    val permissions: List<String>,
    val name: String,
    val description: String,
    @DrawableRes val icon: Int
)

data class PermissionViewPadding(val top: Dp, val bottom: Dp)
