buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.Essential.Gradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Essential.Kotlin}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://oss.jfrog.org/libs-snapshot") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
