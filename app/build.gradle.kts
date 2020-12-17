plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    id("name.remal.check-dependency-updates") version "1.1.6"
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
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
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
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
        exclude("META-INF/*.kotlin_module")
    }

    compileOptions {
        sourceCompatibility = Application.sourceCompat
        targetCompatibility = Application.targetCompat
    }

    kotlinOptions {
        jvmTarget = Application.jvmTarget
    }
}

dependencies {
    "implementation"(platform(Dependencies.Firebase.Bom))

    fun def(vararg strings: String) {
        for (string in strings) implementation(string)
    }

    def(
        Dependencies.Rhino.Engine,
        Dependencies.Rhino.Helper,

        Dependencies.Network.Jsoup,
        Dependencies.Network.Retrofit,
        Dependencies.Network.OkHttp,

        Dependencies.Jetpack.DataStore,

        Dependencies.Rx.Kotlin,
        Dependencies.Rx.Android,
        Dependencies.Rx.Retrofit,

        Dependencies.Essential.AppCompat,
        Dependencies.Essential.Anko,
        Dependencies.Essential.Kotlin,

        Dependencies.Ktx.Core,
        Dependencies.Ktx.Config,
        // Dependencies.Ktx.Fragment,
        Dependencies.Ktx.NavigationUi,
        Dependencies.Ktx.NavigationFragment,
        Dependencies.Ktx.LifeCycleLiveData,

        Dependencies.Di.Hilt,

        Dependencies.Ui.SpotLight,
        Dependencies.Ui.OverlappingPanels,
        Dependencies.Ui.TransformationLayout,
        Dependencies.Ui.Browser,
        Dependencies.Ui.ShapeOfYou,
        Dependencies.Ui.YoYo,
        Dependencies.Ui.Lottie,
        Dependencies.Ui.SuperBottomSheet,
        Dependencies.Ui.SimpleCodeEditor,
        Dependencies.Ui.SmoothBottomBar,
        Dependencies.Ui.Flexbox,
        Dependencies.Ui.JsonViewer,
        Dependencies.Ui.Licenser,
        Dependencies.Ui.Material,
        Dependencies.Ui.Glide,
        Dependencies.Ui.ConstraintLayout,

        Dependencies.Util.GsonConverter,
        Dependencies.Util.YoyoHelper,
        Dependencies.Util.HangulParser,
        Dependencies.Util.AndroidUtils,
        Dependencies.Util.CrashReporter
    )

    kapt(Dependencies.Di.HiltCompiler)
    kapt(Dependencies.Util.GlideCompiler)
}