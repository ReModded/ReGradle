package dev.remodded.regradle.modules

import dev.remodded.regradle.regradleConfiguration
import dev.remodded.regradle.utils.api
import dev.remodded.regradle.utils.implementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class ApiModulePlugin : ModulePlugin(ModuleType.API) {
    override fun apply(project: Project) = with(project) {
        super.apply(project)

        dependencies {
            for (dep in regradleConfiguration.dependencies)
                if (!dep.optional && dep.platformPlugin)
                    api(dep.toDependencyArtifact(project))
                else
                    implementation(dep.toDependencyArtifact(project))
        }
    }
}
