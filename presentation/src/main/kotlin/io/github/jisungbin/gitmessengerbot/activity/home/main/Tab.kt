/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Tab.kt] created by Ji Sungbin on 21. 6. 19. 오후 11:40.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.activity.home.main

sealed class Tab(private val index: Int) {
    object Script : Tab(0)
    object Debug : Tab(1)
    object Kaven : Tab(2)
    object Setting : Tab(3)

    @Suppress("ClassName")
    class from(index: Int) : Tab(index)
}
