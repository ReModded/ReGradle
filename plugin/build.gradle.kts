plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gradle.plugin.publish)
    `kotlin-dsl`
}

group = "dev.remodded"
version = "1.0.0-SNAPSHOT"

repositories {
    maven("https://repo.remodded.dev/repository/ReGradle/")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")

    implementation("com.google.devtools.ksp:symbol-processing-api:${libs.versions.ksp.get()}")
    implementation("com.google.devtools.ksp:symbol-processing-gradle-plugin:${libs.versions.ksp.get()}")

    implementation(libs.kotlin.jvm)
    implementation(libs.shadow)
    implementation(libs.foojay.resolver)

    // PaperMC/Velocity
    implementation("io.papermc.paperweight:paperweight-userdev:1.7.5")
    implementation("xyz.jpenilla:run-task:2.3.1")
    implementation("xyz.jpenilla:resource-factory:1.2.0")

    // Sponge
    compileOnly("org.spongepowered:plugin-meta:0.8.2")
    implementation("org.spongepowered:vanillagradle:0.2.1-SNAPSHOT")
    implementation("org.spongepowered:spongegradle-plugin-development:2.2.1-SNAPSHOT")
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

    publish {
        dependsOn(publishToMavenLocal)
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
        val usernameReModded: String? by project
        val passwordReModded: String? by project
        val repoURL = "https://repo.remodded.dev/repository/" +
                if (version.toString().endsWith("SNAPSHOT"))
                    "maven-snapshots/"
                else
                    "maven-releases/"

        maven {
            name = "ReModded"
            url = uri(repoURL)
            credentials {
                username = usernameReModded
                password = passwordReModded
            }
        }
    }
}
