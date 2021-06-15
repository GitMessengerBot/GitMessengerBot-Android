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
    const val minSdk = 23
    const val targetSdk = 30
    const val compileSdk = 30
    const val jvmTarget = "1.8"
    const val versionCode = 1
    const val versionName = "제발코로나종식되게해주세요"

    val targetCompat = JavaVersion.VERSION_11
    val sourceCompat = JavaVersion.VERSION_11
}

object Versions {
    object Essential {
        const val Python = "9.1.0"
        const val Kotlin = "1.5.10"
        const val Gradle = "7.1.0-alpha02"
        const val CoreKtx = "1.5.0"
        const val Coroutines = "1.5.0"
    }

    object Ui {
        const val LottieCompose = "1.0.0-alpha07-SNAPSHOT"
        const val Glide = "4.12.0"
        const val Browser = "1.3.0"
    }

    object Util {
        const val CrashReporter = "1.1.0"
        const val Gson = "2.8.7"
        const val SerializationJson = "1.1.0"
        const val CheckDependencyUpdates = "1.3.1"
        const val SerializationConverter = "0.8.0"
    }

    object Network {
        const val OkHttp = "4.9.1"
        const val Retrofit = "2.9.0"
    }

    object Jetpack {
        const val Room = "2.3.0"
    }

    object Hilt {
        const val Master = "2.37"
        const val ViewModel = "1.0.0-alpha03"
        const val ViewModelCompiler = "1.0.0"
    }

    object Compose {
        const val Master = "1.0.0-beta08"
        const val Activity = "1.3.0-beta01"
    }

    object Debug {
        const val LeakCanary = "2.7"
    }
}

object Dependencies {
    val debug = listOf(
        "com.squareup.leakcanary:leakcanary-android:${Versions.Debug.LeakCanary}"
    )

    val network = listOf(
        "com.squareup.okhttp3:okhttp:${Versions.Network.OkHttp}",
        "com.squareup.okhttp3:logging-interceptor:${Versions.Network.OkHttp}",
        "com.squareup.retrofit2:retrofit:${Versions.Network.Retrofit}"
    )

    val essential = listOf(
        "androidx.core:core-ktx:${Versions.Essential.CoreKtx}",
        "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Essential.Kotlin}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Essential.Coroutines}"
    )

    val ui = listOf(
        "androidx.browser:browser:${Versions.Ui.Browser}",
        "com.github.bumptech.glide:glide:${Versions.Ui.Glide}",
        "com.airbnb.android:lottie-compose:${Versions.Ui.LottieCompose}"
    )

    val util = listOf(
        "com.balsikandar.android:crashreporter:${Versions.Util.CrashReporter}",
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Util.SerializationJson}",
        "com.squareup.retrofit2:converter-gson:${Versions.Network.Retrofit}",
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.Util.SerializationConverter}",
        "com.google.code.gson:gson:${Versions.Util.Gson}"
    )

    val room = listOf(
        "androidx.room:room-runtime:${Versions.Jetpack.Room}",
        "androidx.room:room-ktx:${Versions.Jetpack.Room}"
    )

    val hilt = listOf(
        "com.google.dagger:hilt-android:${Versions.Hilt.Master}",
        "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.Hilt.ViewModel}"
    )

    val compose = listOf(
        "androidx.activity:activity-compose:${Versions.Compose.Activity}",
        "androidx.compose.compiler:compiler:${Versions.Compose.Master}",
        "androidx.compose.runtime:runtime-livedata:${Versions.Compose.Master}",
        "androidx.compose.material:material:${Versions.Compose.Master}",
        "androidx.compose.material:material-icons-extended:${Versions.Compose.Master}",
        "androidx.compose.ui:ui-tooling:${Versions.Compose.Master}",
        "androidx.compose.ui:ui:${Versions.Compose.Master}"
    )

    val compiler = listOf(
        "androidx.room:room-compiler:${Versions.Jetpack.Room}",
        "com.github.bumptech.glide:compiler:${Versions.Ui.Glide}",
        "com.google.dagger:hilt-android-compiler:${Versions.Hilt.Master}",
        "androidx.hilt:hilt-compiler:${Versions.Hilt.ViewModelCompiler}"
    )
}
