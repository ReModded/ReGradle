plugins {
    kotlin("jvm") version "1.9.22"
    `kotlin-dsl`
    `maven-publish`
}

group = "dev.remodded"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {}

kotlin {
    jvmToolchain(17)
}

gradlePlugin {
    plugins {
        create("dev.remodded.regradle") {
            id = "dev.remodded.regradle"
            implementationClass = "dev.remodded.regradle.ReGradlePlugin"
        }
    }
}