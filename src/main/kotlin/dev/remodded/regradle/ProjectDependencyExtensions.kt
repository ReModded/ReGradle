package dev.remodded.regradle

import org.gradle.api.Project
import org.gradle.api.artifacts.ResolvedDependency


fun Project.includeInJar(dependency: ResolvedDependency): Boolean {
    return getDependencyList().any {
        it.isNotBlank() && dependency.name.startsWith(it)
    }
}

fun Project.getDependencyList(): List<String> {
    return (properties["dependencyList"] as? String?)?.split(',') ?: listOf()
}