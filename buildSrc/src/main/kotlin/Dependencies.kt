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
    const val targetSdk = 31
    const val compileSdk = 31
    const val jvmTarget = "11"
    const val versionCode = 1
    const val versionName = "please_die_fucking_covid"

    val targetCompat = JavaVersion.VERSION_11
    val sourceCompat = JavaVersion.VERSION_11
}

object Versions {
    const val Orbit = "4.3.0"
    const val FirebaseBom = "29.0.0"

    object Essential {
        const val Kotlin = "1.5.31"
        const val CoreKtx = "1.7.0"
        const val Coroutines = "1.5.1"
        const val Gradle = "7.1.0-beta03"
        const val GoogleService = "4.3.10"
    }

    object Bot {
        const val Python = "9.1.0"
        const val Rhino = "1.7.13"
        const val J2V8 = "6.2.1@aar"
    }

    object Ui {
        const val Browser = "1.3.0"
        const val Material = "1.4.0"
    }

    object Util {
        const val Erratum = "1.0.1"
        const val Logeukes = "1.0.1"
        const val Jackson = "2.13.0"
        const val LeakCanary = "2.7"
        const val SoLoader = "0.10.3"
        const val Flipper = "0.119.0"
        const val KeyboardObserver = "1.0.1"
        const val CheckDependencyUpdates = "1.5.0"
    }

    object Network {
        const val Jsoup = "1.14.3"
        const val OkHttp = "4.9.2"
        const val Retrofit = "2.9.0"
    }

    object Jetpack {
        const val Hilt = "2.40.1"
        const val Room = "2.3.0"
    }

    object Compose {
        const val Lottie = "4.2.1"
        const val Master = "1.0.5"
        const val Activity = "1.4.0"
        const val Lifecycle = "2.4.0"
        const val TimeLineView = "1.0.2"
        const val LandscapistCoil = "1.4.2"
        const val Navigation = "2.4.0-alpha09"
        const val ConstraintLayout = "1.0.0-beta01"
    }

    object OssLicense {
        const val Master = "17.0.0"
        const val Classpath = "0.10.4"
    }
}

object Dependencies {
    const val Jsoup = "org.jsoup:jsoup:${Versions.Network.Jsoup}"
    const val Orbit = "org.orbit-mvi:orbit-viewmodel:${Versions.Orbit}"
    const val browser = "androidx.browser:browser:${Versions.Ui.Browser}"
    const val LandscapistCoil =
        "com.github.skydoves:landscapist-coil:${Versions.Compose.LandscapistCoil}"

    const val Hilt = "com.google.dagger:hilt-android:${Versions.Jetpack.Hilt}"
    const val HiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Jetpack.Hilt}"

    const val RoomCompiler = "androidx.room:room-compiler:${Versions.Jetpack.Room}"

    const val FirebaseBom = "com.google.firebase:firebase-bom:${Versions.FirebaseBom}"

    val Essential = listOf(
        "androidx.core:core-ktx:${Versions.Essential.CoreKtx}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Essential.Coroutines}"
    )

    val Bot = listOf(
        "org.mozilla:rhino:${Versions.Bot.Rhino}",
        "com.eclipsesource.j2v8:j2v8:${Versions.Bot.J2V8}"
    )

    val Ui = listOf(
        "com.google.android.material:material:${Versions.Ui.Material}",
        "com.google.android.gms:play-services-oss-licenses:${Versions.OssLicense.Master}"
    )

    val Jackson = listOf(
        "com.fasterxml.jackson.core:jackson-core:${Versions.Util.Jackson}",
        "com.fasterxml.jackson.core:jackson-databind:${Versions.Util.Jackson}",
        "com.fasterxml.jackson.core:jackson-annotations:${Versions.Util.Jackson}",
        "com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.Util.Jackson}"
    )

    val Retrofit = listOf(
        "com.squareup.retrofit2:retrofit:${Versions.Network.Retrofit}",
        "com.squareup.okhttp3:logging-interceptor:${Versions.Network.OkHttp}",
        "com.squareup.retrofit2:converter-jackson:${Versions.Network.Retrofit}"
    )

    val Util = listOf(
        "io.github.jisungbin:erratum:${Versions.Util.Erratum}",
        "io.github.jisungbin:logeukes:${Versions.Util.Logeukes}",
        "io.github.ParkSangGwon:tedkeyboardobserver:${Versions.Util.KeyboardObserver}"
    )

    val Room = listOf(
        "androidx.room:room-ktx:${Versions.Jetpack.Room}",
        "androidx.room:room-runtime:${Versions.Jetpack.Room}"
    )

    val Compose = listOf(
        "androidx.compose.ui:ui:${Versions.Compose.Master}",
        "androidx.compose.ui:ui-tooling:${Versions.Compose.Master}",
        "com.airbnb.android:lottie-compose:${Versions.Compose.Lottie}",
        "androidx.compose.compiler:compiler:${Versions.Compose.Master}",
        "androidx.compose.material:material:${Versions.Compose.Master}",
        "androidx.activity:activity-compose:${Versions.Compose.Activity}",
        "io.github.jisungbin:timelineview:${Versions.Compose.TimeLineView}",
        "androidx.navigation:navigation-compose:${Versions.Compose.Navigation}",
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Compose.Lifecycle}",
        "androidx.constraintlayout:constraintlayout-compose:${Versions.Compose.ConstraintLayout}"
    )

    val Debug = listOf(
        "com.facebook.flipper:flipper:${Versions.Util.Flipper}",
        "com.facebook.soloader:soloader:${Versions.Util.SoLoader}",
        "com.squareup.leakcanary:leakcanary-android:${Versions.Util.LeakCanary}",
        "com.facebook.flipper:flipper-leakcanary2-plugin:${Versions.Util.Flipper}"
    )

    val Firebase = listOf("com.google.firebase:firebase-firestore-ktx")
}
