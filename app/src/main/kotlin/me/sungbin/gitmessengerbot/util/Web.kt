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
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import me.sungbin.gitmessengerbot.R
import me.sungbin.gitmessengerbot.util.extension.toast

object Web {
    sealed class Link(val url: String) {
        object Github : Link("https://github.com")
        object ProjectGithub : Link("https://github.com/GitMessengerBot")
        object PersonalKeyGuide :
            Link("https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/get-personal-access-key.md")

        object ApiGuide :
            Link("https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/api-guide.md")
    }

    fun open(activity: Activity, link: Link) {
        try {
            val builder = CustomTabsIntent.Builder()

            val projectGithubIntent = builder.build().intent
            projectGithubIntent.data = Link.ProjectGithub.url.toUri()

            val projectGithubPendingIntent = PendingIntent.getActivity(
                activity,
                1000,
                projectGithubIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
            builder.addMenuItem(
                activity.getString(R.string.web_menu_project_github),
                projectGithubPendingIntent
            )

            val customTabIntent = builder.build()
            customTabIntent.launchUrl(activity, link.url.toUri())
        } catch (ignored: Exception) {
            toast(activity, activity.getString(R.string.web_toast_non_install_browser))
        }
    }
}
