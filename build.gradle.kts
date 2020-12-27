buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.Essential.Gradle}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.Di.Hilt}")
        classpath("com.google.gms:google-services:${Versions.Essential.Google}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.Jetpack.Navigation}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Essential.Kotlin}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.google.com") }
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}