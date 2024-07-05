package dev.remodded.regradle

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
    return (properties["dependencyList"] as? String?)?.split(',') ?: listOf()
}
