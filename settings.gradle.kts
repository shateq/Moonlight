pluginManagement {
	repositories {
		mavenLocal()
		mavenCentral()
		gradlePluginPortal()
	}
	plugins {
		kotlin("jvm") version "1.8.10"
		id("com.github.johnrengelman.shadow") version "7.1.2"
	}
}
rootProject.name = "Moonlight"
