/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Permission.kt] created by Ji Sungbin on 21. 7. 8. 오후 11:37.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.activity.setup

import androidx.annotation.DrawableRes

object PermissionType {
    const val NotificationRead = "notification-read"
    const val Battery = "battery"
    const val ScopedStorage = "scoped"
}

data class Permission(
    val permissions: List<String>,
    val name: String,
    val description: String,
    @DrawableRes val icon: Int
)
