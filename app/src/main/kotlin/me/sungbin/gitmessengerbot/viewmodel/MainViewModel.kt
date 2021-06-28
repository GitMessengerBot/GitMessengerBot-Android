/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [MainViewModel.kt] created by Ji Sungbin on 21. 6. 14. 오후 9:19.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.viewmodel

import me.sungbin.gitmessengerbot.repo.github.model.GithubData

class MainViewModel private constructor() {
    lateinit var githubData: GithubData

    companion object {
        val instance by lazy { MainViewModel() }
    }
}
