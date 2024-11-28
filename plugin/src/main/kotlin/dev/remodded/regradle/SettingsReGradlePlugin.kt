package dev.remodded.regradle

import dev.remodded.regradle.modules.ApiModulePlugin
import dev.remodded.regradle.modules.CommonModulePlugin
import dev.remodded.regradle.modules.ModuleType
import dev.remodded.regradle.modules.RootModulePlugin
import dev.remodded.regradle.modules.proxy.ProxyModulePlugin
import dev.remodded.regradle.modules.proxy.VelocityModulePlugin
import dev.remodded.regradle.modules.server.PaperModulePlugin
import dev.remodded.regradle.modules.server.ServerModulePlugin
import dev.remodded.regradle.modules.server.SpongeModulePlugin
import dev.remodded.regradle.utils.getOptional
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.extra
import org.gradle.toolchains.foojay.FoojayToolchainsPlugin
import java.net.URI

class SettingsReGradlePlugin : Plugin<Settings> {

    override fun apply(settings: Settings): Unit = with(settings) {
        // Set the root project name if property `$name` is set
        extra.getOptional<String>("\$name")?.apply {
            rootProject.name = this
        }

        println("Applying ReGradle settings to ${rootProject.name}")

        addPluginRepositories()

        apply<FoojayToolchainsPlugin>()

        val regradleConfiguration = ReGradleModulesConfigurationImpl(this)
        extensions.add("regradleConfiguration", regradleConfiguration)
        gradle.extensions.add("regradleConfiguration", regradleConfiguration)

        var initialized = false
        gradle.beforeProject {
            if (!initialized) {
                initialized = true
                regradleConfiguration.postInit(settings)
            }

            val moduleType = regradleConfiguration.getType(this)

            if (moduleType == ModuleType.None) return@beforeProject

            when (moduleType) {
                ModuleType.ROOT -> apply<RootModulePlugin>()
                ModuleType.API -> apply<ApiModulePlugin>()
                ModuleType.COMMON -> apply<CommonModulePlugin>()
                ModuleType.PROXY -> apply<ProxyModulePlugin>()
                ModuleType.SERVER -> apply<ServerModulePlugin>()
                ModuleType.PAPER -> apply<PaperModulePlugin>()
                ModuleType.SPONGE -> apply<SpongeModulePlugin>()
                ModuleType.VELOCITY -> apply<VelocityModulePlugin>()
                else -> throw IllegalArgumentException("Unknown module type: $moduleType")
            }
        }
    }
}

fun Settings.addPluginRepositories() {
    pluginManagement {
        repositories {
            mavenLocal()
            maven {
                url = URI("https://repo.remodded.dev/repository/maven-public/")
                name = "ReModded Repository"
            }
            maven {
                url = URI("https://repo.remodded.dev/repository/Fabric/")
                name = "Remodded Fabric proxy"
            }
            maven {
                url = URI("https://repo.remodded.dev/repository/Sponge/")
                name = "Remodded Sponge proxy"
            }
            maven {
                url = URI("https://repo.remodded.dev/repository/PaperMC/")
                name = "Remodded Sponge proxy"
            }
            gradlePluginPortal()
        }
    }
}
