plugins {
    java
    kotlin("jvm") version "1.7.10"

    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("application")
}

version = "2.0.1"
group = "shateq.java"
base.archivesName.set("${project.name}-jdk17-$version")
description = "Discord bot attempt"

repositories.mavenCentral()

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-beta.1") { //JDA
        exclude("opus-java")
    }
    implementation("com.sedmelluq:lavaplayer:1.3.77")
    implementation("org.jetbrains:annotations:23.0.0")
    // jedis
    // Utils
    implementation("io.github.cdimascio:dotenv-java:2.3.1")
    implementation("org.slf4j:slf4j-simple:2.0.5")
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))
application.mainClass.set("shateq.java.moonlight.MoonlightBot")

tasks {
    processResources {
        filteringCharset = "UTF-8"
        inputs.property("version", project.version)
    }
    compileJava {
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
}
