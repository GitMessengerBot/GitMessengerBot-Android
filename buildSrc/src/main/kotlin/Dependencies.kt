/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [Dependencies.kt] created by Ji Sungbin on 21. 5. 21. 오후 4:41.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

import org.gradle.api.JavaVersion

object Application {
    const val minSdk = 24
    const val targetSdk = 30
    const val compileSdk = 30
    const val jvmTarget = "11"
    const val versionCode = 1
    const val versionName = "please_die_fucking_covid"

    val targetCompat = JavaVersion.VERSION_11
    val sourceCompat = JavaVersion.VERSION_11
}

object Versions {
    object Essential {
        const val Python = "9.1.0"
        const val Kotlin = "1.5.21"
        const val CoreKtx = "1.6.0"
        const val Coroutines = "1.5.1"
        const val Gradle = "7.1.0-alpha05"
    }

    object Bot {
        const val Rhino = "1.7.13"
        const val J2V8 = "6.2.1@aar"
    }

    object Ui {
        const val Browser = "1.3.0"
        const val Material = "1.4.0"
        const val TimeLineView = "1.0.2"
        const val LottieCompose = "4.1.0"
        const val FancyBottomBar = "1.0.1"
        const val LandscapistCoil = "1.3.4"
        const val ConstraintLayout = "1.0.0-beta01"
    }

    object Util {
        const val Pluto = "1.0.6"
        const val Timber = "5.0.1"
        const val LeakCanary = "2.7"
        const val KeyboardObserver = "1.0.1"
        const val ViewColorGenerator = "v0.1"
        const val CheckDependencyUpdates = "1.5.0"
    }

    object Network {
        const val Jsoup = "1.14.1"
        const val OkHttp = "4.9.1"
        const val Retrofit = "2.9.0"
    }

    object Jetpack {
        const val Room = "2.3.0"
    }

    object Jackson {
        const val Master = "2.12.5"
    }

    object Hilt {
        const val Master = "2.38.1"
    }

    object Compose {
        const val Master = "1.0.1"
        const val Activity = "1.3.1"
    }

    object Lifecycle {
        const val Master = "2.3.1"
        const val Compose = "1.0.0-alpha07"
    }

    object OssLicense {
        const val Master = "17.0.0"
        const val Classpath = "0.10.4"
    }
}

object Dependencies {
    const val jsoup = "org.jsoup:jsoup:${Versions.Network.Jsoup}"

    const val browser = "androidx.browser:browser:${Versions.Ui.Browser}"
    const val LandscapistCoil =
        "com.github.skydoves:landscapist-coil:${Versions.Ui.LandscapistCoil}"

    const val hilt = "com.google.dagger:hilt-android:${Versions.Hilt.Master}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Hilt.Master}"

    const val roomCompiler = "androidx.room:room-compiler:${Versions.Jetpack.Room}"

    const val livedata =
        "androidx.lifecycle:lifecycle-livedata-core-ktx:${Versions.Lifecycle.Master}"

    const val jacksonModule =
        "com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.Jackson.Master}"

    val bot = listOf(
        "org.mozilla:rhino:${Versions.Bot.Rhino}",
        "com.eclipsesource.j2v8:j2v8:${Versions.Bot.J2V8}"
    )

    val debug = listOf(
        "com.mocklets:pluto:${Versions.Util.Pluto}",
        "com.squareup.leakcanary:leakcanary-android:${Versions.Util.LeakCanary}"
    )

    val release = listOf("com.mocklets:pluto-no-op:${Versions.Util.Pluto}")

    val jackson = listOf(
        "com.fasterxml.jackson.core:jackson-core:${Versions.Jackson.Master}",
        "com.fasterxml.jackson.core:jackson-databind:${Versions.Jackson.Master}",
        "com.fasterxml.jackson.core:jackson-annotations:${Versions.Jackson.Master}"
    )

    val retrofit = listOf(
        "com.squareup.okhttp3:okhttp:${Versions.Network.OkHttp}",
        "com.squareup.retrofit2:retrofit:${Versions.Network.Retrofit}"
    )

    val retrofitutil = listOf(
        "com.squareup.okhttp3:logging-interceptor:${Versions.Network.OkHttp}",
        "com.squareup.retrofit2:converter-jackson:${Versions.Network.Retrofit}"
    )

    val essential = listOf(
        "androidx.core:core-ktx:${Versions.Essential.CoreKtx}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Essential.Coroutines}"
    )

    val ui = listOf(
        "io.github.jisungbin:timelineview:${Versions.Ui.TimeLineView}",
        "com.google.android.material:material:${Versions.Ui.Material}",
        "com.airbnb.android:lottie-compose:${Versions.Ui.LottieCompose}",
        "io.github.jisungbin:fancybottombar:${Versions.Ui.FancyBottomBar}",
        "com.google.android.gms:play-services-oss-licenses:${Versions.OssLicense.Master}",
        "androidx.constraintlayout:constraintlayout-compose:${Versions.Ui.ConstraintLayout}"
    )

    val util = listOf(
        "com.jakewharton.timber:timber:${Versions.Util.Timber}",
        "io.github.ParkSangGwon:tedkeyboardobserver:${Versions.Util.KeyboardObserver}",
        "com.github.MindorksOpenSource:ViewColorGenerator:${Versions.Util.ViewColorGenerator}"
    )

    val room = listOf(
        "androidx.room:room-ktx:${Versions.Jetpack.Room}",
        "androidx.room:room-runtime:${Versions.Jetpack.Room}"
    )

    val compose = listOf(
        "androidx.compose.ui:ui:${Versions.Compose.Master}",
        "androidx.compose.ui:ui-tooling:${Versions.Compose.Master}",
        "androidx.compose.compiler:compiler:${Versions.Compose.Master}",
        "androidx.compose.material:material:${Versions.Compose.Master}",
        "androidx.activity:activity-compose:${Versions.Compose.Activity}",
        "androidx.compose.runtime:runtime-livedata:${Versions.Compose.Master}",
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Lifecycle.Compose}"
    )
}
