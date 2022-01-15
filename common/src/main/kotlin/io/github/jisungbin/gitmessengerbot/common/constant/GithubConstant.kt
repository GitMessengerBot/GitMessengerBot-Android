/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [GithubConfig.kt] created by Ji Sungbin on 21. 8. 30. 오후 5:02
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.common.constant

object GithubConstant {
    const val BaseUrl = "https://github.com"
    const val BaseApiUrl = "https://api.github.com"

    const val DataPath = "${PathConstant.AppStorage}/github-data.json"

    const val DefaultBranch = "main"
    const val DefaultRepoDescription = "Created by GitMessengerBot"
    const val DefaultCommitMessage = "Commited by GitMessengerBot" // TODO: pattern
}
