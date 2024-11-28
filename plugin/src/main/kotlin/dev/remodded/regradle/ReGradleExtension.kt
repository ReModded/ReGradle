package dev.remodded.regradle

import dev.remodded.regradle.modules.ModuleType
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*


val Project.regradle get() = this.extensions.getByType<ReGradleExtension>()
fun Project.regradle() = this.extensions.findByType<ReGradleExtension>()
fun Project.regradle(block: ReGradleExtension.() -> Unit) = this.extensions.configure(block)

class ReGradleExtension(
    val module: ModuleType,
    val mcVersion: MCVersion,
) {
}
