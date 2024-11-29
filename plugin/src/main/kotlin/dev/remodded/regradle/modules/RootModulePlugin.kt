package dev.remodded.regradle.modules

import dev.remodded.regradle.regradleConfiguration
import dev.remodded.regradle.utils.build
import dev.remodded.regradle.utils.clean
import org.gradle.api.Project
import org.gradle.kotlin.dsl.repositories

class RootModulePlugin : ModulePlugin(ModuleType.ROOT) {
    override fun apply(project: Project) = with(project) {
        repositories {
            mavenCentral()
        }

        val build = tasks.register("build") {
            group = "build"
        }
        val clean = tasks.register("clean") {
            group = "build"
        }

        regradleConfiguration.allProjects.forEach { subproject ->
            if (subproject == project)
                return@forEach

            subproject.afterEvaluate {
                build.get().dependsOn(tasks.build.get())
                clean.get().dependsOn(tasks.clean.get())
            }
        }
    }
}
