package dev.remodded.regradle.modules.proxy

import dev.remodded.regradle.modules.ModulePlugin
import dev.remodded.regradle.modules.ModuleType
import dev.remodded.regradle.plugin.getPluginProps
import dev.remodded.regradle.project.markAsBuildTarget
import dev.remodded.regradle.project.markAsNeedShadow
import dev.remodded.regradle.regradleConfiguration
import dev.remodded.regradle.utils.compileOnly
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*
import xyz.jpenilla.resourcefactory.velocity.VelocityConvention
import xyz.jpenilla.resourcefactory.velocity.VelocityPluginJson
import xyz.jpenilla.runvelocity.RunVelocityPlugin
import xyz.jpenilla.runvelocity.task.RunVelocity

class VelocityModulePlugin : ModulePlugin(ModuleType.VELOCITY) {
    override fun apply(project: Project): Unit = with(project) {
        super.apply(this)

        apply<RunVelocityPlugin>()
        apply<VelocityConvention>()

        markAsBuildTarget()
        markAsNeedShadow()

        repositories {
            maven("https://repo.remodded.dev/repository/PaperMC/")
            maven("https://repo.remodded.dev/repository/maven-public/")
        }

        val velocityVersion = regradleConfiguration.velocityVersion

        dependencies {
            compileOnly("com.velocitypowered:velocity-api:$velocityVersion")
        }

        tasks {
            named<RunVelocity>("runVelocity") {
                velocityVersion(velocityVersion.toString())

                // TODO: add dependencies
                //  pluginJars(getPluginFromMaven("dev.remodded.recore:ReCore-Velocity:1.0.0-SNAPSHOT"))
            }
        }

        val props = getPluginProps()

        configure<VelocityPluginJson> {
            id.set(props.id)
            name.set(props.name)
            version.set(props.version)

            url.set(props.url)
            authors.add(props.author)
            description.set(props.description)

            main.set(props.entryPoint)

            // TODO: add dependencies
            // dependency("recore", false)
        }
    }
}
