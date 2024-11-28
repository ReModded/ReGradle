import dev.remodded.regradle.MCVersion
import dev.remodded.regradle.regradle
import dev.remodded.regradle.utils.Version

pluginManagement {
    includeBuild("plugin") // ReGradle plugin
    repositories {
        maven("https://repo.remodded.dev/repository/ReGradle/") {
            name = "ReModded ReGradle Repository"
        }
    }
}

plugins {
    id("dev.remodded.regradle") version "1.0.0-SNAPSHOT"
}

// Override the root project name
rootProject.name = "ReGradle-parent"

regradle {
    mcVersion(MCVersion.V1_21_3)

    root("retest")

    api("retest:api")
    common("retest:common")
    server("retest:server")
    proxy("retest:proxy")

    paper("retest:server:paper")

    sponge("retest:server:sponge-api12", MCVersion.V1_21_1)

    velocity("retest:proxy:velocity", Version(3, 3, 0, "SNAPSHOT"))

    addPlatformDependency("dev.remodded", "ReCore", "1.0.0-SNAPSHOT")
}
