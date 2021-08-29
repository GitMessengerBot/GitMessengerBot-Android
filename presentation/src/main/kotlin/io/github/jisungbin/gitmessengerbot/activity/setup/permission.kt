/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [permission.kt] created by Ji Sungbin on 21. 7. 8. 오후 11:37.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.setup

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object PermissionType {
    const val NotificationRead = "notification-read"
    const val Wear = "android-wear"
    const val ScopedStorage = "scoped"
}

data class Permission(
    val permissions: List<String>,
    val name: String,
    val description: String,
    @DrawableRes val icon: Int,
)

@Immutable
interface PermissionViewPadding {
    @Stable
    val top: Dp

    @Stable
    val bottom: Dp
}

private class PermissionViewPaddingImpl(override val top: Dp, override val bottom: Dp) :
    PermissionViewPadding

fun PermissionViewPadding(top: Dp = 0.dp, bottom: Dp = 0.dp): PermissionViewPadding =
    PermissionViewPaddingImpl(top, bottom)
