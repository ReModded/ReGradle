package dev.remodded.regradle.project

import dev.remodded.regradle.utils.getOptional
import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra

private const val BUILD_TARGET_NAME_ID = "isBuildTarget"

/**
 * Marks the project as a build target.
 */
fun Project.markAsBuildTarget() {
    this.extra[BUILD_TARGET_NAME_ID] = true
}

/**
 * Checks if the project is a build target.
 *
 * @return True if the project is a build target, false otherwise.
 */
fun Project.isBuildTarget(): Boolean {
    return this.extra.getOptional(BUILD_TARGET_NAME_ID) ?: false
}
