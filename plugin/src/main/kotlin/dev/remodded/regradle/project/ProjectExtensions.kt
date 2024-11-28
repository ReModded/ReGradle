package dev.remodded.regradle.project

import dev.remodded.regradle.regradle
import org.gradle.api.Project
import org.gradle.kotlin.dsl.support.uppercaseFirstChar

/**
 * Get a Project suffix.
 *
 * @return Project suffix.
 */
fun Project.getProjectSuffix(): String {
    val regradle = regradle
    return regradle.module.platformPackageName(regradle.mcVersion)
        .lowercase()
        .replace('-', '_')
        .replace("api", "API")
        .uppercaseFirstChar()
}
