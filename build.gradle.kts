plugins {
    kotlin("jvm") version "1.9.22"
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "1.2.1"
}

group = "dev.remodded"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {}

kotlin {
    jvmToolchain(17)
}

gradlePlugin {
    website = "https://github.com/ReModded/ReGradle"
    vcsUrl = "https://github.com/ReModded/ReGradle"
    plugins {
        create("ReGradle") {
            id = "dev.remodded.regradle"
            displayName = name
            description = "ReGradle is a Gradle plugin for ReModded projects."
            implementationClass = "dev.remodded.regradle.ReGradlePlugin"
            tags = listOf("ReModded", "Kotlin", "Minecraft Plugins")
        }
    }
}

publishing {
    repositories {
        val username: String? by project
        val password: String? by project
        maven {
            name = "ReModded"
            url = uri("https://repo.remodded.dev/repository/maven-snapshots/")
            credentials {
                this.username = username
                this.password = password
            }
        }
    }
}