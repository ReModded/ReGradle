plugins {
    kotlin("jvm") version "2.0.0"
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "1.2.1"
}

group = "dev.remodded"
version = "1.0.0-SNAPSHOT"

repositories {
    maven("https://repo.remodded.dev/repository/maven-central/")
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:2.0.0-1.0.22")
    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:2.0.0-1.0.22")
}

kotlin {
    jvmToolchain(21)
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
