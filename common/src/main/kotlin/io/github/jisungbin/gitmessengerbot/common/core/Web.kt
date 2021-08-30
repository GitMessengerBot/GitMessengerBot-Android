/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Web.kt] created by Ji Sungbin on 21. 8. 30. 오후 5:05
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.common.core

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import io.github.jisungbin.gitmessengerbot.common.extension.toast
import io.github.jisungbin.gitmessengerbot.util.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup

object Web {
    sealed class Link(val url: String) {
        object Github : Link("https://github.com")
        object ProjectGithub : Link("https://github.com/GitMessengerBot")
        object DonateOpenChat : Link("https://open.kakao.com/me/duck__bin")
        object PersonalKeyGuide :
            Link("https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/get-personal-access-key.md")

        object ApiGuide :
            Link("https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/api-guide.md")

        class Custom(address: String) : Link(address)
    }

    suspend fun parse(address: String): String = coroutineScope {
        return@coroutineScope async(Dispatchers.IO) {
            Jsoup.connect(address).get().wholeText()
        }
    }.await()

    fun open(context: Context, link: Link) {
        try {
            val builder = CustomTabsIntent.Builder()
            builder.build()
                .intent
                .addCategory(Intent.CATEGORY_BROWSABLE)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

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
        } catch (exception: Exception) {
            println(exception)
            toast(context, context.getString(R.string.web_toast_non_install_browser))
        }
    }
}
