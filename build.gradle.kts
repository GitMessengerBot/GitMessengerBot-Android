/*
 * GitMessengerBot © 2021 지성빈 & 구환. all rights reserved.
 * GitMessengerBot license is under the GPL-3.0.
 *
 * [build.gradle.kts] created by Ji Sungbin on 21. 5. 21. 오후 4:41.
 *
 * Please see: https://github.com/GitMessengerBot/GitMessengerBot-Android/blob/master/LICENSE.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://chaquo.com/maven") }
    }

    dependencies {
        classpath("com.chaquo.python:gradle:${Versions.Essential.Python}")
        classpath("com.android.tools.build:gradle:${Versions.Essential.Gradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Essential.Kotlin}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.Hilt.Master}")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        maven { setUrl("https://chaquo.com/maven") }
        maven { setUrl("https://oss.jfrog.org/libs-snapshot") }
        maven { setUrl("https://oss.sonatype.org/content/repositories/snapshots") }
    }

    afterEvaluate {
        tasks.withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-Xopt-in=kotlin.RequiresOptIn",
                    "-Xopt-in=kotlin.OptIn",
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=true"
                )
            }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
