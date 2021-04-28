import org.gradle.api.JavaVersion

object Application {
    const val minSdk = 23
    const val targetSdk = 30
    const val compileSdk = 30
    const val versionCode = 1
    const val jvmTarget = "1.8"
    const val versionName = "제발코로나종식되게해주세요_1"

    val targetCompat = JavaVersion.VERSION_1_8
    val sourceCompat = JavaVersion.VERSION_1_8
}

object Versions {
    object Essential {
        const val Python = "9.1.0"
        const val Kotlin = "1.4.32"
        const val Gradle = "7.0.0-alpha14"
        const val CoreKtx = "1.3.2"
    }

    object Ui {
        const val LottieCompose = "1.0.0-alpha07-SNAPSHOT"
        const val Glide = "0.7.0"
        const val Browser = "1.3.0"
    }

    object Util {
        const val CrashReporter = "1.1.0"
        const val Gson = "2.8.6"
    }

    object Network {
        const val OkHttp = "4.9.1"
        const val Retrofit = "2.9.0"
    }

    object Jetpack {
        const val Room = "2.3.0"
    }

    object Compose {
        const val Version = "1.0.0-beta04"
        const val Activity = "1.3.0-alpha05"
    }
}

object Dependencies {
    val classpath = listOf(
        "com.chaquo.python:gradle:${Versions.Essential.Python}",
        "com.android.tools.build:gradle:${Versions.Essential.Gradle}",
        "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Essential.Kotlin}"
    )

    val network = listOf(
        "com.squareup.okhttp3:okhttp:${Versions.Network.OkHttp}",
        "com.squareup.okhttp3:logging-interceptor:${Versions.Network.OkHttp}",
        "com.squareup.retrofit2:retrofit:${Versions.Network.Retrofit}"
    )

    val essential = listOf(
        "androidx.core:core-ktx:${Versions.Essential.CoreKtx}",
        "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Essential.Kotlin}"
    )

    val ui = listOf(
        "androidx.browser:browser:${Versions.Ui.Browser}",
        "com.google.accompanist:accompanist-glide:${Versions.Ui.Glide}",
        "com.airbnb.android:lottie-compose:${Versions.Ui.LottieCompose}"
    )

    val util = listOf(
        "com.balsikandar.android:crashreporter:${Versions.Util.CrashReporter}",
        "com.squareup.retrofit2:converter-gson:${Versions.Network.Retrofit}",
        "com.google.code.gson:gson:${Versions.Util.Gson}"
    )

    var room = listOf(
        "androidx.room:room-runtime:${Versions.Jetpack.Room}",
        "androidx.room:room-ktx:${Versions.Jetpack.Room}"
    )

    var compose = listOf(
        "androidx.compose.runtime:runtime-livedata:${Versions.Compose.Version}",
        "androidx.activity:activity-compose:${Versions.Compose.Activity}",
        "androidx.compose.ui:ui:${Versions.Compose.Version}",
        "androidx.compose.material:material:${Versions.Compose.Version}",
        "androidx.compose.material:material-icons-extended:${Versions.Compose.Version}",
        "androidx.compose.ui:ui-tooling:${Versions.Compose.Version}"
    )

    val compiler = listOf(
        "androidx.room:room-compiler:${Versions.Jetpack.Room}"
    )
}
