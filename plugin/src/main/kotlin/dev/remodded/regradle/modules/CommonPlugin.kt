package dev.remodded.regradle.modules

import com.google.devtools.ksp.gradle.KspExtension
import dev.remodded.regradle.plugin.getPluginProps
import dev.remodded.regradle.utils.ManifestReader
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.maven

class CommonPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        // Add the KSP plugin and dependencies
        val manifest = ManifestReader(javaClass)
        println("ReGradle version: ${manifest.getValue("ReGradle-Version")}")

        project.plugins.apply("com.google.devtools.ksp")
        project.repositories.maven("https://repo.remodded.dev/repository/maven-public/")
        project.dependencies.add("ksp", "dev.remodded:regradle:${manifest.getValue("ReGradle-Version")}")

        project.extensions.findByType(KspExtension::class.java)?.apply {
            val props = project.getPluginProps(false)
            props.forEach {
                arg(it.key, it.value)
            }
        }
    }
}
