/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [FancyItem.kt] created by Ji Sungbin on 21. 5. 21. 오후 4:38.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.ui.fancybottombar

import androidx.annotation.DrawableRes

data class FancyItem(val title: String = "", @DrawableRes val icon: Int? = null, val id: Int = 0)
