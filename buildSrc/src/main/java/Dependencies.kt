import org.gradle.api.JavaVersion

object Application {
    const val minSdk = 23
    const val targetSdk = 30
    const val compileSdk = 30
    const val jvmTarget = "1.8"
    const val versionCode = 1
    const val versionName = "1.0.0"

    val targetCompat = JavaVersion.VERSION_1_8
    val sourceCompat = JavaVersion.VERSION_1_8
}

object Versions {
    object Firebase {
        const val Bom = "26.2.0"
    }

    object Rhino {
        const val Engine = "1.7.13"
        const val Helper = "1.5.2"
    }

    object Network {
        const val Jsoup = "1.13.1"
        const val Retrofit = "2.9.0"
        const val OkHttp = "4.9.0"
        const val LoggingInterceptor = "4.9.0"
    }

    object Jetpack {
        const val Navigation = "2.3.2"
        const val DataStore = "1.0.0-alpha05"
        const val Compose = "1.0.0-alpha09"
    }

    object Rx {
        const val Kotlin = "3.0.1"
        const val Android = "3.0.0"
        const val Retrofit = "2.9.0"
    }

    object Essential {
        const val AppCompat = "1.2.0"
        const val Anko = "0.10.8"
        const val Gradle = "7.0.0-alpha03"
        const val Kotlin = "1.4.21"
        const val Google = "4.3.3"
    }

    object Ktx {
        const val Core = "1.3.2"
        const val LifeCycleLiveData = "2.2.0"
    }

    object Di {
        const val Hilt = "2.28-alpha"
    }

    object Ui {
        const val SpotLight = "2.0.3"
        const val OverlappingPanels = "0.1.1"
        const val TransformationLayout = "1.0.6"
        const val Browser = "1.3.0"
        const val ShapeOfView = "1.4.7"
        const val Flexbox = "2.0.1"
        const val SmoothBottomBar = "1.7.6"
        const val Lottie = "3.5.0"
        const val SimpleCodeEditor = "2.0.4"
        const val Material = "1.2.0-alpha06"
        const val Glide = "4.11.0"
        const val ConstraintLayout = "2.0.4"
    }

    object Util {
        const val GsonConverter = "2.9.0"
        const val AndroidUtils = "4.2.3"
        const val CrashReporter = "1.1.0"
    }
}

object Dependencies {
    object Firebase {
        const val Bom = "com.google.firebase:firebase-bom:${Versions.Firebase.Bom}"
    }

    object Rhino {
        const val Engine = "org.mozilla:rhino:${Versions.Rhino.Engine}"
        const val Helper = "com.faendir.rhino:rhino-android:${Versions.Rhino.Helper}"
    }

    object Jetpack {
        const val DataStore =
            "androidx.datastore:datastore-preferences:${Versions.Jetpack.DataStore}"

        object Compose {
            const val Ui = "androidx.compose.ui:ui:${Versions.Jetpack.Compose}"
            const val Foundation =
                "androidx.compose.foundation:foundation:${Versions.Jetpack.Compose}"
            const val Material = "androidx.compose.material:material:${Versions.Jetpack.Compose}"
            const val MaterialIconsCore =
                "androidx.compose.material:material-icons-core:${Versions.Jetpack.Compose}"
            const val MaterialIconExtended =
                "androidx.compose.material:material-icons-extended:${Versions.Jetpack.Compose}"
            const val RuntimeLiveData =
                "androidx.compose.runtime:runtime-livedata:${Versions.Jetpack.Compose}"
            const val RuntimeRxJava2 =
                "androidx.compose.runtime:runtime-rxjava2:${Versions.Jetpack.Compose}"
            const val UiTooling = "androidx.compose.ui:ui-tooling:${Versions.Jetpack.Compose}"
        }
    }

    object Network {
        const val Jsoup = "org.jsoup:jsoup:${Versions.Network.Jsoup}"
        const val Retrofit = "com.squareup.okhttp3:okhttp:${Versions.Network.OkHttp}"
        const val OkHttp = "com.squareup.retrofit2:retrofit:${Versions.Network.Retrofit}"
        const val LoggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.Network.LoggingInterceptor}"
    }

    object Rx {
        const val Kotlin = "io.reactivex.rxjava3:rxkotlin:${Versions.Rx.Kotlin}"
        const val Android = "io.reactivex.rxjava3:rxandroid:${Versions.Rx.Android}"
        const val Retrofit = "com.squareup.retrofit2:adapter-rxjava3:${Versions.Rx.Retrofit}"
    }

    object Essential {
        const val AppCompat = "androidx.appcompat:appcompat:${Versions.Essential.AppCompat}"
        const val Anko = "org.jetbrains.anko:anko:${Versions.Essential.Anko}"
        const val Kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.Essential.Kotlin}"
    }

    object Ktx {
        const val Config = "com.google.firebase:firebase-config-ktx"
        const val NavigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${Versions.Jetpack.Navigation}"
        const val NavigationUi =
            "androidx.navigation:navigation-ui-ktx:${Versions.Jetpack.Navigation}"
        const val Core = "androidx.core:core-ktx:${Versions.Ktx.Core}"
        const val LifeCycleLiveData =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.Ktx.LifeCycleLiveData}"
    }

    object Di {
        const val Hilt = "com.google.dagger:hilt-android:${Versions.Di.Hilt}"
        const val HiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.Di.Hilt}"
    }

    object Ui {
        const val Flexbox = "com.google.android:flexbox:${Versions.Ui.Flexbox}"
        const val SmoothBottomBar =
            "com.github.ibrahimsn98:SmoothBottomBar:${Versions.Ui.SmoothBottomBar}"
        const val SpotLight = "com.github.takusemba:spotlight:${Versions.Ui.SpotLight}"
        const val OverlappingPanels =
            "com.github.discord:OverlappingPanels:${Versions.Ui.OverlappingPanels}"
        const val TransformationLayout =
            "com.github.skydoves:transformationlayout:${Versions.Ui.TransformationLayout}"
        const val Browser = "androidx.browser:browser:${Versions.Ui.Browser}"
        const val ShapeOfYou = "com.github.florent37:shapeofview:${Versions.Ui.ShapeOfView}"
        const val Lottie = "com.airbnb.android:lottie:${Versions.Ui.Lottie}"
        const val SimpleCodeEditor =
            "com.github.sungbin5304:SimpleCodeEditor:${Versions.Ui.SimpleCodeEditor}"
        const val Material = "com.google.android.material:material:${Versions.Ui.Material}"
        const val Glide = "com.github.bumptech.glide:glide:${Versions.Ui.Glide}"
        const val ConstraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.Ui.ConstraintLayout}"
    }

    object Util {
        const val GsonConverter =
            "com.squareup.retrofit2:converter-gson:${Versions.Util.GsonConverter}"
        const val GlideCompiler = "com.github.bumptech.glide:compiler:${Versions.Ui.Glide}"
        const val AndroidUtils = "com.github.sungbin5304:SBT:${Versions.Util.AndroidUtils}"
        const val CrashReporter =
            "com.balsikandar.android:crashreporter:${Versions.Util.CrashReporter}"
    }
}