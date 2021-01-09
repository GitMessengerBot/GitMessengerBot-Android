plugins {
    id("com.android.application")
    id("name.remal.check-dependency-updates") version "1.1.6"
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
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
        // compose = true
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
        // useIR = true
    }

    /*composeOptions {
        kotlinCompilerVersion = Versions.Essential.Kotlin
        kotlinCompilerExtensionVersion = Versions.Jetpack.Compose
    }*/
}

dependencies {
    "implementation"(platform(Dependencies.Firebase.Bom))
    // implementation(files("./libs/j2v8-6.2.0.aar"))

    implementation("com.eclipsesource.j2v8:j2v8:6.2.0@aar")

    fun def(vararg strings: String) {
        for (string in strings) implementation(string)
    }

    def(
        Dependencies.Rhino.Engine,
        Dependencies.Rhino.Helper,

        Dependencies.Network.Jsoup,
        Dependencies.Network.Retrofit,
        Dependencies.Network.OkHttp,
        Dependencies.Network.LoggingInterceptor,

        Dependencies.Jetpack.DataStore,
        Dependencies.Jetpack.SecurityCrypto,

        /*Dependencies.Jetpack.Compose.Ui,
        Dependencies.Jetpack.Compose.Foundation,
        Dependencies.Jetpack.Compose.Material,
        Dependencies.Jetpack.Compose.MaterialIconsCore,
        Dependencies.Jetpack.Compose.MaterialIconExtended,
        Dependencies.Jetpack.Compose.RuntimeRxJava2,
        Dependencies.Jetpack.Compose.RuntimeLiveData,
        Dependencies.Jetpack.Compose.UiTooling,*/

        Dependencies.Rx.Kotlin,
        Dependencies.Rx.Android,
        Dependencies.Rx.Retrofit,

        Dependencies.Essential.AppCompat,
        Dependencies.Essential.Anko,
        Dependencies.Essential.Kotlin,

        Dependencies.Ktx.Core,
        Dependencies.Ktx.Config,
        Dependencies.Ktx.NavigationUi,
        Dependencies.Ktx.NavigationFragment,
        Dependencies.Ktx.LifeCycleLiveData,

        Dependencies.Di.Hilt,

        Dependencies.Ui.SpotLight,
        Dependencies.Ui.OverlappingPanels,
        Dependencies.Ui.TransformationLayout,
        Dependencies.Ui.Browser,
        Dependencies.Ui.ShapeOfYou,
        Dependencies.Ui.Lottie,
        Dependencies.Ui.SimpleCodeEditor,
        Dependencies.Ui.SmoothBottomBar,
        Dependencies.Ui.Flexbox,
        Dependencies.Ui.Material,
        Dependencies.Ui.Glide,
        Dependencies.Ui.ConstraintLayout,

        Dependencies.Util.GsonConverter,
        Dependencies.Util.AndroidUtils,
        Dependencies.Util.CrashReporter
    )

    kapt(Dependencies.Di.HiltCompiler)
    kapt(Dependencies.Util.GlideCompiler)
}
