package dev.remodded.regradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.plugins.PluginAware
import org.slf4j.LoggerFactory

class ReGradlePlugin : Plugin<PluginAware> {

    private val logger = LoggerFactory.getLogger("ReGradle")

    override fun apply(target: PluginAware) {
        when (target) {
            is Project -> apply(target)
            is Settings -> target.plugins.apply(SettingsReGradlePlugin::class.java)
            is Gradle -> return
            else -> throw IllegalArgumentException("Expected target to be a Project or Settings, but was a " + target.javaClass)
        }

        target.plugins
    }

    fun apply(project: Project) {
        logger.info("Applying ReGradle plugin to ${project.name}")
    }
}
