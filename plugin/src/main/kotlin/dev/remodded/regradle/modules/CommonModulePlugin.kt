package dev.remodded.regradle.modules

import com.google.devtools.ksp.gradle.KspExtension
import com.google.devtools.ksp.gradle.KspGradleSubplugin
import dev.remodded.regradle.plugin.getPluginProps
import dev.remodded.regradle.regradleConfiguration
import dev.remodded.regradle.utils.ManifestReader
import dev.remodded.regradle.utils.implementation
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import java.util.*

class CommonModulePlugin : ModulePlugin(ModuleType.COMMON) {

    override fun apply(project: Project): Unit = with(project) {
        super.apply(this)

        // Add the KSP plugin and dependencies
        val manifest = ManifestReader(CommonModulePlugin::class.java)
        val reGradleVersion = manifest.getValue("ReGradle-Version")
        if (reGradleVersion == null) {
            println("ReGradle version not found in manifest")
            for (e in manifest.manifest.mainAttributes)
                println(" - ${e.key}: ${e.value}")

            throw GradleException("ReGradle version not found in manifest")
        }
        println("ReGradle version: $reGradleVersion")

        apply<KspGradleSubplugin>()

        repositories {
            maven("https://repo.remodded.dev/repository/ReGradle/")
        }

        dependencies {
            add("ksp", "dev.remodded:regradle:$reGradleVersion")
            regradleConfiguration.getModuleProject(ModuleType.API)?.let { add("api", it) }

            for (dep in regradleConfiguration.dependencies)
                if (dep.optional || !dep.platformPlugin)
                    implementation(dep.toDependencyArtifact(project))
        }

        extensions.findByType<KspExtension>()?.apply {
            val props = getPluginProps()
            props.forEach {
                arg(it.key, encodeToBase64(it.value))
            }
            arguments.forEach { arg ->
                println("KSP [${arg.key}=${arg.value}]")
            }
        }
    }

    private fun encodeToBase64(value: String): String {
        val encoder = Base64.getEncoder()
        return encoder.encodeToString(value.toByteArray())
    }
}
