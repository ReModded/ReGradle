package dev.remodded.regradle

import dev.remodded.regradle.modules.ModuleType
import dev.remodded.regradle.utils.Version
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import org.gradle.internal.impldep.kotlinx.serialization.Serializable

abstract class ReGradleSettingsExtensionImpl(private val settings: Settings): ReGradleModulesConfiguration {
    protected data class Module(
        val name: String,
        val type: ModuleType,
        val mcVersion: MCVersion,
        var project: Project? = null,
    )

    protected val modules: MutableList<Module> = arrayListOf()

    @Serializable
    data class Dependency(val group: String, val name: String, val version: String)
    val dependencies = hashSetOf<Dependency>()

    var velocityVersion: Version = Version.UNKNOWN
        private set

    final override var mcVersion = MCVersion.LATEST
        private set

    override fun mcVersion(version: MCVersion) {
        mcVersion = version
    }

    override fun root(module: String) {
        module(module, ModuleType.ROOT, MCVersion.UNKNOWN)
    }

    override fun api(module: String) {
        module(module, ModuleType.API, MCVersion.UNKNOWN)
    }

    override fun common(module: String) {
        module(module, ModuleType.COMMON, MCVersion.UNKNOWN)
    }

    override fun proxy(module: String) {
        module(module, ModuleType.PROXY, MCVersion.UNKNOWN)
    }

    override fun server(module: String) {
        module(module, ModuleType.SERVER, MCVersion.UNKNOWN)
    }

    override fun velocity(module: String, velocityVersion: Version) {
        this.velocityVersion = velocityVersion
        module(module, ModuleType.VELOCITY, MCVersion.UNKNOWN)
    }

    override fun paper(module: String, version: MCVersion) {
        module(module, ModuleType.PAPER, version)
    }

    override fun sponge(module: String, version: MCVersion) {
        module(module, ModuleType.SPONGE, version)
    }

    override fun module(module: String, moduleType: ModuleType, version: MCVersion) {
        if (moduleType.singular)
            for (m in modules) {
                if (m.type == moduleType)
                    throw GradleException("Module type $moduleType already exists for module ${m.name}")
            }

        println("[ReGradle] Adding module: $module as $moduleType")

        settings.include(":$module")
        modules.add(Module(module, moduleType, version))
    }

    override fun addDependency(artifact: String) {
        val parts = artifact.split(":")
        if (parts.size < 3)
            throw GradleException("Missing artifact name or version.")

        addDependency(parts[0], parts[1], parts.drop(2).joinToString())
    }

    override fun addDependency(group: String, name: String, version: String) =
        addDependency(Dependency(group, name, version))

    fun addDependency(dep: Dependency) {
        dependencies.add(dep)
    }

    fun postInit(settings: Settings) {
        modules.forEach {
            it.project = settings.gradle.rootProject.project(":${it.name}")
        }
    }
}
