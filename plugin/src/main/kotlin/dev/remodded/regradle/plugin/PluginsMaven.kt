package dev.remodded.regradle.plugin

import org.gradle.api.Project
import java.io.File

/**
 * Get a plugin from the maven repository.
 *
 * @param pluginArtifact The plugin artifact to get.
 * @return The plugin file.
 */
fun Project.getPluginFromMaven(pluginArtifact: String): File {
    val deps = project.dependencies.create(pluginArtifact)

    // Resolve the configuration to get the files
    val tmpConfiguration = configurations.getByName("shadowRuntimeElements").copy().apply {
        dependencies.add(deps)
    }

    val resolvedConfiguration = tmpConfiguration.resolvedConfiguration
    val firstLvlModules = resolvedConfiguration.firstLevelModuleDependencies
    val firstLvlModule = firstLvlModules.first()
    val moduleArtifacts = firstLvlModule.moduleArtifacts
    val artifact = moduleArtifacts.first()

    return artifact.file
}

/**
 * Get a plugin from the maven repository.
 *
 * @param pluginArtifact The plugin artifact to get.
 * @return The plugin file.
 */
fun Project.tryGetPluginFromMaven(pluginArtifact: String): File? {
    return try {
        getPluginFromMaven(pluginArtifact)
    } catch (e: Exception) {
        null
    }
}
