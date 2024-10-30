package dev.remodded.regradle.project

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.util.*

/**
 * Get a Project suffix.
 *
 * @return Project suffix.
 */
fun Project.getProjectSuffix(): String {
    val name = project.name.replace("api", "API")
    return name.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
}

fun Project.tryApplyToSubproject(subprojectName: String, plugin: Class<out Plugin<Project>>): Boolean {
    val subproject = subprojects.find { it.name.equals(subprojectName, true) } ?: return false
    subproject.plugins.apply(plugin)
    return true
}
