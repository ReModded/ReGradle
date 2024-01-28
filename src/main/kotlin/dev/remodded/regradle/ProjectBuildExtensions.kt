package dev.remodded.regradle

import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra


private const val BUILD_TARGET_NAME_ID = "isBuildTarget"
fun Project.markAsBuildTarget() {
    this.extra[BUILD_TARGET_NAME_ID] = true
}

fun Project.isBuildTarget(): Boolean {
    return this.extra.getOptional(BUILD_TARGET_NAME_ID) ?: false
}
