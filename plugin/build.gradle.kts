plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gradle.plugin.publish)
    `kotlin-dsl`
}

group = "dev.remodded"
version = "1.0.0-SNAPSHOT"

repositories {
    maven("https://repo.remodded.dev/repository/maven-public/")
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:2.0.0-1.0.22")
    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:2.0.0-1.0.22")
}

kotlin {
    jvmToolchain(21)
}

tasks {
    jar {
        manifest {
            attributes["ReGradle-Version"] = project.version
        }
    }
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
        val repoURL = "https://repo.remodded.dev/repository/" +
                if (version.toString().endsWith("SNAPSHOT"))
                    "maven-snapshots/"
                else
                    "maven-releases/"

        maven {
            name = "ReModded"
            url = uri(repoURL)
            credentials {
                this.username = username
                this.password = password
            }
        }
    }
}
