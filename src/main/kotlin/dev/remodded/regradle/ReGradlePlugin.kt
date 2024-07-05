package dev.remodded.regradle

import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.maven

class ReGradlePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.logger.info("Applying ReGradle to ${project.name}")

        // Add the KSP plugin and dependencies
        project.plugins.apply("com.google.devtools.ksp")
        project.repositories.maven("https://repo.remodded.dev/repository/maven-public/")
        project.dependencies.add("ksp", "dev.remodded:ReGradle:1.0.0-SNAPSHOT")

        project.extensions.findByType(KspExtension::class.java)?.apply {
            val props = project.getPluginProps(false)
            props.forEach {
                arg(it.key, it.value)
            }
        }
    }
}
