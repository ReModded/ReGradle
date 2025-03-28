package dev.remodded.regradle.modules.server

import dev.remodded.regradle.modules.ModulePlugin
import dev.remodded.regradle.modules.ModuleType
import dev.remodded.regradle.plugin.getPluginProps
import dev.remodded.regradle.plugin.tryGetPluginFromMaven
import dev.remodded.regradle.project.markAsBuildTarget
import dev.remodded.regradle.project.markAsNeedShadow
import dev.remodded.regradle.regradle
import dev.remodded.regradle.regradleConfiguration
import dev.remodded.regradle.utils.implementation
import io.papermc.paperweight.userdev.PaperweightUser
import io.papermc.paperweight.userdev.PaperweightUserDependenciesExtension
import io.papermc.paperweight.userdev.PaperweightUserExtension
import io.papermc.paperweight.userdev.ReobfArtifactConfiguration
import io.papermc.paperweight.util.configureTask
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import xyz.jpenilla.resourcefactory.paper.PaperConvention
import xyz.jpenilla.resourcefactory.paper.PaperPluginYaml
import xyz.jpenilla.runpaper.RunPaperPlugin
import xyz.jpenilla.runpaper.task.RunServer

class PaperModulePlugin : ModulePlugin(ModuleType.PAPER) {
    override fun apply(project: Project): Unit = with(project) {
        super.apply(this)

        apply<PaperweightUser>()
        apply<RunPaperPlugin>()
        apply<PaperConvention>()

        markAsBuildTarget()
        markAsNeedShadow()

        repositories {
            maven("https://repo.remodded.dev/repository/PaperMC/")
        }

        val mcVersion = regradle.mcVersion

        configure<PaperweightUserExtension> {
            injectPaperRepository = false
            reobfArtifactConfiguration = ReobfArtifactConfiguration.MOJANG_PRODUCTION
        }

        dependencies {
            configure<PaperweightUserDependenciesExtension> {
                paperDevBundle("${mcVersion}-R0.1-SNAPSHOT")
            }
            for (dep in regradleConfiguration.dependencies)
                implementation(dep.toDependencyArtifact(project))
        }

        tasks {
            configureTask<RunServer>("runServer") {
                minecraftVersion(mcVersion.toString())

                for (dep in regradleConfiguration.dependencies) {
                    val pluginFile = tryGetPluginFromMaven(dep.toDependencyArtifact(project))
                    if (pluginFile != null)
                        pluginJars(pluginFile)
                }
            }
        }

        val props = getPluginProps()

        configure<PaperPluginYaml> {
            name.set(props.name)
            version.set(props.version)
            description.set(props.description)

            main.set(props.entryPoint)
            authors.add(props.author)
            apiVersion.set(mcVersion.toString())

            website.set(props.url)

            dependencies {
                for (dep in regradleConfiguration.dependencies) {
                    server(dep.name, PaperPluginYaml.Load.BEFORE, required = !dep.optional, joinClasspath = true)
                }
            }
        }
    }
}
