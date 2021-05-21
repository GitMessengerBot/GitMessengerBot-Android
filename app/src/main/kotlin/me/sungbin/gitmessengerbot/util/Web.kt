/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Web.kt] created by Ji Sungbin on 21. 5. 21. 오후 4:40.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.util

import android.app.Activity
import android.app.PendingIntent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

object Web {
    sealed class Type {
        object Github : Type()
        object ProjectGithub : Type()
        object PersonalKeyGuide : Type()
        object ApiGuide : Type()
    }

    // todo: 크롬 사용 가능한지 체크
    fun open(activity: Activity, type: Type) {
        val url = when (type) {
            is Type.Github -> "https://github.com"
            is Type.ProjectGithub -> "https://github.com/GitMessengerBot"
            is Type.PersonalKeyGuide -> "https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/get-personal-access-key.md"
            is Type.ApiGuide -> "https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/api-guide.md"
        }

        val builder = CustomTabsIntent.Builder()

        val gotoProjectGithubIntent = builder.build().intent
        gotoProjectGithubIntent.data = Uri.parse("https://github.com/GitMessengerBot")

        val projectGithubIntent = PendingIntent.getActivity(
            activity,
            1000,
            gotoProjectGithubIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        builder.addMenuItem("프로젝트 깃허브", projectGithubIntent)

        val customTabIntent = builder.build()
        customTabIntent.launchUrl(activity, Uri.parse(url))
    }
}
