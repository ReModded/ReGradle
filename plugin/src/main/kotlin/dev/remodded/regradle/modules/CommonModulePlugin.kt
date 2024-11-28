package dev.remodded.regradle.modules

import com.google.devtools.ksp.gradle.KspExtension
import com.google.devtools.ksp.gradle.KspGradleSubplugin
import dev.remodded.regradle.plugin.getPluginProps
import dev.remodded.regradle.regradleConfiguration
import dev.remodded.regradle.utils.ManifestReader
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.findByType

class CommonModulePlugin : ModulePlugin(ModuleType.COMMON) {
    override fun apply(project: Project): Unit = with(project) {
        super.apply(this)

        // Add the KSP plugin and dependencies
        val manifest = ManifestReader(javaClass)
        println("ReGradle version: ${manifest.getValue("ReGradle-Version")}")

        apply<KspGradleSubplugin>()

        dependencies {
            add("ksp", "dev.remodded:regradle:${manifest.getValue("ReGradle-Version")}")
            regradleConfiguration.getModuleProject(ModuleType.API)?.let { add("api", it) }
        }

        extensions.findByType<KspExtension>()?.apply {
            val props = getPluginProps()
            props.forEach {
                arg(it.key, it.value)
            }
        }
    }
}
