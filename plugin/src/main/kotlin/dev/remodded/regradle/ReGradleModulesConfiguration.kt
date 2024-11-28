package dev.remodded.regradle

import dev.remodded.regradle.modules.ModuleType
import dev.remodded.regradle.utils.Version
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType


val Settings.regradle get() = this.extensions.getByType<ReGradleModulesConfiguration>()
fun Settings.regradle(configure: ReGradleModulesConfiguration.() -> Unit) {
    settings.extensions.configure<ReGradleModulesConfiguration>(configure)
}

interface ReGradleModulesConfiguration {
    val mcVersion: MCVersion
    fun mcVersion(version: MCVersion)

    fun root(module: String)

    fun api(module: String)
    fun common(module: String)

    fun proxy(module: String)
    fun server(module: String)

    fun velocity(module: String, velocityVersion: Version)

    fun paper(module: String, version: MCVersion = mcVersion)
    fun sponge(module: String, version: MCVersion = mcVersion)

    fun module(module: String, moduleType: ModuleType, version: MCVersion = mcVersion)

    fun addDependency(artifact: String)
    fun addDependency(group: String, name: String, version: String)
}
