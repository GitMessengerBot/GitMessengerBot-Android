/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Web.kt] created by Ji Sungbin on 21. 5. 21. 오후 4:40.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package me.sungbin.gitmessengerbot.util

import android.app.PendingIntent
import android.content.Context
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
        class Custom(address: String) : Link(address)
    }

    fun open(context: Context, link: Link) {
        try {
            val builder = CustomTabsIntent.Builder()

            val projectGithubIntent = builder.build().intent
            projectGithubIntent.data = Link.ProjectGithub.url.toUri()

            val projectGithubPendingIntent = PendingIntent.getActivity(
                context,
                1000,
                projectGithubIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
            builder.addMenuItem(
                context.getString(R.string.web_menu_project_github),
                projectGithubPendingIntent
            )

            val customTabIntent = builder.build()
            customTabIntent.launchUrl(context, link.url.toUri())
        } catch (ignored: Exception) {
            toast(context, context.getString(R.string.web_toast_non_install_browser))
        }
    }
}
