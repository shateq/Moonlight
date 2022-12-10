pluginManagement {
    plugins {
        kotlin("jvm") version "1.7.10"
        id("com.github.johnrengelman.shadow") version "7.1.2"
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}
rootProject.name = "Moonlight"