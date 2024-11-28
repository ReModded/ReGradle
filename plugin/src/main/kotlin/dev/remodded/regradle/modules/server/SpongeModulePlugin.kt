package dev.remodded.regradle.modules.server

import dev.remodded.regradle.modules.ModulePlugin
import dev.remodded.regradle.modules.ModuleType
import dev.remodded.regradle.plugin.getPluginProps
import dev.remodded.regradle.plugin.tryGetPluginFromMaven
import dev.remodded.regradle.project.markAsBuildTarget
import dev.remodded.regradle.project.markAsNeedShadow
import dev.remodded.regradle.regradle
import dev.remodded.regradle.regradleConfiguration
import dev.remodded.regradle.utils.compileOnly
import dev.remodded.regradle.utils.implementation
import io.papermc.paperweight.util.configureTask
import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec
import org.gradle.kotlin.dsl.*
import org.spongepowered.gradle.plugin.SpongePluginExtension
import org.spongepowered.gradle.plugin.SpongePluginGradle
import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.gradle.vanilla.MinecraftExtension
import org.spongepowered.gradle.vanilla.VanillaGradle
import org.spongepowered.gradle.vanilla.repository.MinecraftPlatform
import org.spongepowered.plugin.metadata.model.PluginDependency

class SpongeModulePlugin : ModulePlugin(ModuleType.SPONGE) {
    override fun apply(project: Project) = with(project) {
        super.apply(this)

        markAsBuildTarget()
        markAsNeedShadow()

        repositories {
            maven("https://repo.remodded.dev/repository/Sponge/")
        }

        apply<VanillaGradle>()
        apply<SpongePluginGradle>()

        val mcVersion = regradle.mcVersion

        dependencies {
            implementation("org.spongepowered:sponge:${mcVersion}-${mcVersion.spongeAPIVersion}")

            compileOnly("org.spongepowered:mixin:0.8.7-SNAPSHOT")

            for (dep in regradleConfiguration.dependencies)
                implementation(dep.toDependencyArtifact(project))
        }

        val props = getPluginProps()

        configure<MinecraftExtension> {
            version(mcVersion.toString())
            platform(MinecraftPlatform.SERVER)
            injectRepositories(false)

            this.platform()
        }

        tasks {
            configureTask<JavaExec>("runServer") {
                for (dep in regradleConfiguration.dependencies) {
                    val pluginFile = tryGetPluginFromMaven(dep.toDependencyArtifact(project))
                    if (pluginFile != null)
                        classpath += files(pluginFile)
                }
            }
        }

        configure<SpongePluginExtension> {
            apiVersion(mcVersion.spongeAPIVersion.toString())
            license("MIT")
            loader {
                name(PluginLoaders.JAVA_PLAIN)
                version("1.0")
            }
            plugin(props.id) {
                displayName(props.name)
                entrypoint(props.entryPoint)
                description(props.description)
                links {
                    homepage(props.url)
                    source(props.urlSrc)
                    issues(props.urlIssues)
                }
                contributor(props.author) {}
                dependency("spongeapi") {
                    loadOrder(PluginDependency.LoadOrder.AFTER)
                    optional(false)
                }

                for (dep in regradleConfiguration.dependencies) {
                    dependency(dep.name.lowercase()) {
                        version(dep.version)
                        loadOrder(PluginDependency.LoadOrder.AFTER)
                        optional(dep.optional)
                    }
                }
            }
        }
    }
}
