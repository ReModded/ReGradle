package dev.remodded.regradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class ReGradlePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.logger.info("Applying ReGradle to ${target.name}")
    }
}