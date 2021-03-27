plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("name.remal.check-dependency-updates") version "1.2.2"
}

android {
    compileSdkVersion(Application.compileSdk)

    defaultConfig {
        minSdkVersion(Application.minSdk)
        targetSdkVersion(Application.targetSdk)
        versionCode = Application.versionCode
        versionName = Application.versionName
        multiDexEnabled = true
        setProperty("archivesBaseName", "$versionName ($versionCode)")
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    buildFeatures {
        compose = true
    }

    kapt {
        correctErrorTypes = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.Version
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
    }

    packagingOptions {
        exclude("META-INF/library_release.kotlin_module")
    }

    compileOptions {
        sourceCompatibility = Application.sourceCompat
        targetCompatibility = Application.targetCompat
    }

    kotlinOptions {
        jvmTarget = Application.jvmTarget
        useIR = true
    }
}

// Ignored red-line. It's working well.
dependencies {
    Dependencies.essential.forEach(::implementation)
    Dependencies.network.forEach(::implementation)
    Dependencies.rx.forEach(::implementation)
    Dependencies.di.forEach(::implementation)
    Dependencies.ui.forEach(::implementation)
    Dependencies.util.forEach(::implementation)
    Dependencies.compose.forEach(::implementation)
    Dependencies.room.forEach(::implementation)
    Dependencies.compiler.forEach(::kapt)
}
