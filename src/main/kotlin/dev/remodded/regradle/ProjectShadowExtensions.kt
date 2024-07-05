package dev.remodded.regradle

import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra


private const val NEEDS_SHADOW = "needsShadow"

/**
 * Marks the project as needing shadowing.
 */
fun Project.markAsNeedShadow() {
    this.extra[NEEDS_SHADOW] = true
}

/**
 * Checks if the project needs shadowing.
 *
 * @return True if the project needs shadowing, false otherwise.
 */
fun Project.needsShadow(): Boolean {
    return this.extra.getOptional(NEEDS_SHADOW) ?: false
}
