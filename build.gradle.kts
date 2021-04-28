buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://chaquo.com/maven") }
    }

    dependencies { Dependencies.classpath.forEach { def -> classpath(def) } }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://chaquo.com/maven") }
        maven { url = uri("https://oss.jfrog.org/libs-snapshot") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
