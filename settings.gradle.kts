pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.kikugie.dev/snapshots")
        maven("https://maven.fabricmc.net")
    }
}

plugins {
    id("io.github.bizcub.multiloader") version "0.8+"
}

multiloader {
    match("26.1.2", fb, nf, fg)
    match("1.21.11",fb, nf, fg)
    match("1.21.8", fb, nf, fg)
}
