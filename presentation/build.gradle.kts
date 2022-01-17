/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [build.gradle.kts] created by Ji Sungbin on 21. 5. 21. 오후 4:41.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
    id("dagger.hilt.android.plugin")
    id("com.google.android.gms.oss-licenses-plugin")
    id("name.remal.check-dependency-updates") version Versions.Util.CheckDependencyUpdates
}

android {
    compileSdk = Application.compileSdk

    defaultConfig {
        minSdk = Application.minSdk
        targetSdk = Application.targetSdk
        versionCode = Application.versionCode
        versionName = Application.versionName
        multiDexEnabled = true
        setProperty("archivesBaseName", "$versionName ($versionCode)")

        vectorDrawables {
            useSupportLibrary = true
        }

        ndk {
            abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
        }

        buildConfigField("long", "TIMESTAMP", "${System.currentTimeMillis()}L")
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true

            ndk {
                debugSymbolLevel = "FULL"
            }
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.Master
    }

    sourceSets {
        getByName("main").run {
            java.srcDirs("src/main/kotlin")
        }
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
    val allProjects = listOf(
        projects.core,
        projects.common,
        projects.dataKaven,
        projects.dataGithub,
        projects.domainKaven,
        projects.domainGithub
    )

    implementation(Dependencies.Hilt)
    implementation(Dependencies.Orbit)
    implementation(Dependencies.Jsoup)
    implementation(Dependencies.LandscapistCoil) {
        exclude(group = "androidx.appcompat", module = "appcompat")
        exclude(group = "androidx.appcompat", module = "appcompat-resources")
    }

    allProjects.forEach(::implementation)
    implementation(platform(Dependencies.FirebaseBom))

    Dependencies.Ui.forEach(::implementation)
    Dependencies.Util.forEach(::implementation)
    Dependencies.Jackson.forEach(::implementation)
    Dependencies.Compose.forEach(::implementation)
    Dependencies.Retrofit.forEach(::implementation)
    Dependencies.Firebase.forEach(::implementation)

    Dependencies.Debug.forEach(::debugImplementation)

    kapt(Dependencies.HiltCompiler) // TODO: ksp
}
