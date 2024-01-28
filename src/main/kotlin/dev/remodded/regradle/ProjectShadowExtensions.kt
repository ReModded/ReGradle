package dev.remodded.regradle

import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra


private const val NEEDS_SHADOW = "needsShadow"
fun Project.markAsNeedShadow() {
    this.extra[NEEDS_SHADOW] = true
}

fun Project.needsShadow(): Boolean {
    return this.extra.getOptional(NEEDS_SHADOW) ?: false
}