# ReGradle

Simple gradle plugin for developing [ReCore](https://github.com/ReModded/ReCore) based Minecraft plugins for multiple platforms.


## How to use

settings.gradle.kts
```kts
// Add ReGradle repository
pluginManagement {
    repositories {
        maven("https://repo.remodded.dev/repository/ReGradle/") {
            name = "ReModded ReGradle Repository"
        }
    }
}

// Apply ReGradle to settings.gradle.kts
plugins {
    id("dev.remodded.regradle") version "1.0.0-SNAPSHOT"
}

regradle {
    mcVersion(MCVersion.V1_21_3)

    // Define your project modules
    root("")

    api("api")
    common("common")
    server("server")
    proxy("proxy")

    paper("server:paper")

    sponge("server:sponge-api12", MCVersion.V1_21_1)

    velocity("proxy:velocity", Version(3, 3, 0, "SNAPSHOT"))

    // Add dependencies to your modules
    addPlatformDependency("dev.remodded", "ReCore", "1.0.0-SNAPSHOT")
}
```
