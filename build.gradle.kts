plugins {
	java
	application
	kotlin("jvm")
	id("com.github.johnrengelman.shadow")
}

version = "2.0.1"
group = "shateq.java"
base.archivesName.set("${project.name}-jdk17")
description = "Discord bot attempt 2021"

repositories {
	mavenCentral()
	maven("https://m2.dv8tion.net/releases")
	maven("https://jitpack.io")
}

dependencies {
	implementation("net.dv8tion:JDA:5.0.0-beta.1")
	//implementation("club.minnced:discord-webhooks:0.8.2")
	implementation("com.github.walkyst:lavaplayer-fork:1.3.99.1") //lavaplayer

	implementation("com.fasterxml.jackson.core:jackson-core:2.14.0")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.14.0")

	//implementation("redis.clients:jedis:4.3.1")
	implementation("org.jetbrains:annotations:23.1.0")
	implementation("org.slf4j:slf4j-simple:2.0.5")
}

application.mainClass.set("shateq.moonlight.MoonlightBot")
java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

tasks {
	processResources {
		filteringCharset = "UTF-8"
		inputs.property("version", project.version)
	}
	withType<JavaCompile> {
		options.encoding = "UTF-8"
		options.release.set(17)
	}
	shadowJar {
		minimize()
	}
	jar {
		manifest.attributes(
			"Implementation-Title" to project.name,
			"Implementation-Version" to project.version
		)
	}
	wrapper {
		gradleVersion = "7.6"
	}
}
