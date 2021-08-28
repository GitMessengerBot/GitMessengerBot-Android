/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [SecretConfig.kt] created by Ji Sungbin on 21. 7. 16. 오전 5:52.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.data.github.secret

object SecretConfig {
    const val GithubOauthClientId = "bfda375f63ceed0c8a9e"
    const val GithubOauthClientSecret = "39c1cc9a8abca5aa5100e9d14f74126c6fcb8e70"
    const val GithubOauthAddress =
        "https://github.com/login/oauth/authorize?client_id=$GithubOauthClientId&scope=repo,user"
}
