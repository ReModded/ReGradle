pluginManagement {
    includeBuild("plugin") // ReGradle plugin
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "ReGradle-parent"

include("retest")
