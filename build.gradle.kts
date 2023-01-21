plugins {
	java
	application
	kotlin("jvm")
	id("com.github.johnrengelman.shadow")
}

version = "2.3.0"
group = "shateq.java"
base.archivesName.set("${project.name}-jdk17")
description = "Discord bot attempt 2021"

repositories {
	mavenCentral()
	mavenLocal() //may be useful
	maven("https://m2.dv8tion.net/releases")
	maven("https://jitpack.io")
}

dependencies {
	implementation("net.dv8tion:JDA:5.0.0-beta.1") {
		exclude(module = "opus-java")
	}
	implementation("com.github.minndevelopment:jda-ktx:0.10.0-beta.1")
	implementation("com.github.walkyst:lavaplayer-fork:1.3.99.2")

	implementation("com.github.MinnDevelopment:jda-reactor:1.6.0")
	implementation("io.projectreactor:reactor-core:3.5.1")

	implementation("org.jetbrains:annotations:23.1.0")
	//implementation("redis.clients:jedis:4.3.1")
	implementation("org.slf4j:slf4j-simple:2.0.5")
}

application.mainClass.set("shateq.moonlight.MoonlightBot")
java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

tasks {
	processResources {
		filteringCharset = "UTF-8"
	}
	withType<JavaCompile> {
		options.encoding = "UTF-8"
		options.release.set(17)
	}
	shadowJar {
		minimize()
		manifest.attributes(
			"Implementation-Title" to project.name,
			"Implementation-Version" to project.version
		)
	}
}
