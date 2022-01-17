/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [settings.gradle.kts] created by Ji Sungbin on 21. 5. 21. 오후 4:42.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(
    ":presentation",
    ":domain-github",
    ":domain-kaven",
    ":data-kaven",
    ":data-github",
    ":core",
    ":common"
)
rootProject.name = "GitMessengerBot"
