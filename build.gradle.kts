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
	maven("https://jitpack.io")
}

dependencies {
	implementation("net.dv8tion:JDA:5.0.0-beta.1")
	implementation("club.minnced:discord-webhooks:0.8.2")

	implementation("org.jetbrains:annotations:23.0.0")
	implementation("org.slf4j:slf4j-simple:2.0.5")
	//implementation("com.sedmelluq:lavaplayer:1.3.77")
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
