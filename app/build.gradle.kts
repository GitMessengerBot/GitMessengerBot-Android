plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("name.remal.check-dependency-updates") version "1.0.211"
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(Application.compileSdk)
    defaultConfig {
        minSdkVersion(Application.minSdk)
        targetSdkVersion(Application.targetSdk)
        versionCode = Application.versionCode
        versionName = Application.versionName
        multiDexEnabled = true
        setProperty("archivesBaseName", "v$versionName ($versionCode)")
    }

    buildFeatures {
        dataBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/library_release.kotlin_module")
    }

    compileOptions {
        sourceCompatibility = Application.sourceCompat
        targetCompatibility = Application.targetCompat
    }

    kotlinOptions {
        jvmTarget = Application.jvmTarget
    }
}

kapt {
    correctErrorTypes = true
    generateStubs = true
}

dependencies {
    fun def(vararg strings: String) {
        for (string in strings) implementation(string)
    }

    def(
        Dependencies.Network.Jsoup,
        Dependencies.Network.Retrofit,
        Dependencies.Network.OkHttp,

        Dependencies.Rx.Kotlin,
        Dependencies.Rx.Android,
        Dependencies.Rx.Retrofit,

        Dependencies.Essential.AppCompat,
        Dependencies.Essential.Anko,
        Dependencies.Essential.Kotlin,

        Dependencies.Ktx.Core,
        Dependencies.Ktx.Fragment,

        Dependencies.Di.Hilt,

        Dependencies.Ui.YoYo,
        Dependencies.Ui.Lottie,
        Dependencies.Ui.MaterialPopupMenu,
        Dependencies.Ui.SimpleCodeEditor,
        Dependencies.Ui.JsonViewer,
        Dependencies.Ui.Licenser,
        Dependencies.Ui.Material,
        Dependencies.Ui.Glide,
        Dependencies.Ui.CardView,
        Dependencies.Ui.ConstraintLayout,

        Dependencies.Utils.YoyoHelper,
        Dependencies.Utils.HangulParser,
        Dependencies.Utils.AndroidUtils,
        Dependencies.Utils.CrashReporter
    )

    kapt(Dependencies.Di.HiltCompiler)
    kapt(Dependencies.Utils.GlideCompiler)
}