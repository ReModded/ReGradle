package dev.remodded.regradle

import dev.remodded.regradle.modules.CommonPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.slf4j.LoggerFactory

class ReGradlePlugin : Plugin<Project> {

    private val logger = LoggerFactory.getLogger("ReGradle")

    override fun apply(project: Project) {
        project.logger.info("Applying ReGradle to ${project.name}")

        if (project != project.rootProject)
            logger.warn("ReGradle should only be applied to the root project")

        project.tryApplyToSubproject("common", CommonPlugin::class.java)
    }


    private fun Project.tryApplyToSubproject(subprojectName: String, plugin: Class<out Plugin<Project>>): Boolean {
        val subproject = subprojects.find { it.name.equals(subprojectName, true) } ?: return false
        subproject.plugins.apply(plugin)
        return true
    }
}
