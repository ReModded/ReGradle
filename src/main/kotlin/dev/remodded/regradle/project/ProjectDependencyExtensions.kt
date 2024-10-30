package dev.remodded.regradle.project

import org.gradle.api.Project
import org.gradle.api.artifacts.ResolvedDependency

/**
 * Checks if the project should include the dependency in the jar.
 *
 * @param dependency The dependency to check.
 * @return True if the project should include the dependency in the jar, false otherwise.
 */
fun Project.includeInJar(dependency: ResolvedDependency): Boolean {
    return getDependencyList().any {
        it.isNotBlank() && dependency.name.startsWith(it)
    }
}

/**
 * Gets the dependency list from the project.
 *
 * @return The dependency list.
 */
fun Project.getDependencyList(): List<String> {
    val dependencyList = properties["dependencyList"] as? String ?: ""
    val dependencies = dependencyList.split(",").toMutableSet()

    // Add all subprojects to the dependency list
    for (proj in rootProject.subprojects)
        dependencies.add("${proj.group}:${proj.name}")

    return dependencies.toList()
}
