/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [ExceptionBuilder.kt] created by Ji Sungbin on 21. 8. 28. 오후 10:19
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

package io.github.jisungbin.gitmessengerbot.common.exception

class CoreException(override val message: String?) : Exception()

class DataGithubException(override val message: String?) : Exception()

class DataKavenException(override val message: String?) : Exception()

class PresentationException(override val message: String?) : Exception()

class CommonException(override val message: String?) : Exception()
