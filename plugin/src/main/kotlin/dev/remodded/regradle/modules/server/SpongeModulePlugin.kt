package dev.remodded.regradle.modules.server

import dev.remodded.regradle.modules.ModulePlugin
import dev.remodded.regradle.modules.ModuleType
import dev.remodded.regradle.plugin.getPluginProps
import dev.remodded.regradle.project.markAsBuildTarget
import dev.remodded.regradle.project.markAsNeedShadow
import dev.remodded.regradle.regradle
import dev.remodded.regradle.utils.compileOnly
import dev.remodded.regradle.utils.implementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
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
        }

        val props = getPluginProps()

        configure<MinecraftExtension> {
            version(mcVersion.toString())
            platform(MinecraftPlatform.SERVER)
            injectRepositories(false)
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
                // todo: dependencies
                //  dependency("recore") {
                //      version("1.0.+")
                //      loadOrder(PluginDependency.LoadOrder.AFTER)
                //      optional(false)
                //  }
            }
        }
    }
}
