package dev.remodded.regradle

import dev.remodded.regradle.modules.ModuleType
import org.gradle.api.Project
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.*

val Project.regradleConfiguration get() = gradle.extensions.getByType<ReGradleModulesConfigurationImpl>()

class ReGradleModulesConfigurationImpl(settings: Settings): ReGradleSettingsExtensionImpl(settings) {
    val allProjects: Collection<Project> get() = modules.map { it.project }.requireNoNulls()

    fun getType(project: Project): ModuleType {
        return getModule(project)?.type ?: ModuleType.None
    }

    fun getModuleProject(moduleType: ModuleType): Project? {
        return getModule(moduleType)?.project
    }

    fun getModuleProjectWithFallback(moduleType: ModuleType): Project? {
        var module = moduleType
        do {
            val currentModule = getModuleProject(module)
            if (currentModule != null)
                return currentModule

            module = module.dependency
        } while (module.dependency != ModuleType.None)
        return null
    }

    fun createModuleExtension(project: Project) {
        val module = getModule(project) ?: throw IllegalArgumentException("Project ${project.name} is not configured as a module")

        project.extensions.add("regradle", ReGradleExtension(module.type, module.mcVersion))
    }

    private fun getModule(moduleType: ModuleType): Module? {
        return modules.find { it.type == moduleType }
    }

    private fun getModule(project: Project): Module? {
        return modules.find { it.project == project }
    }

}
