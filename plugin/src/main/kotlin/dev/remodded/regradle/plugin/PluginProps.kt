package dev.remodded.regradle.plugin

import dev.remodded.regradle.modules.ModuleType
import dev.remodded.regradle.regradle
import dev.remodded.regradle.utils.PascalCaseMapDelegate
import org.gradle.api.Project
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.support.uppercaseFirstChar

/**
 * Plugin properties.
 *
 * @property data The plugin properties.
 */
class PluginProps(
    private val data: Map<String, String>
) : Map<String, String> by data {

    private val delegate = PascalCaseMapDelegate(data)

    val id: String by delegate
    val name: String by delegate
    val group: String by delegate
    val version: String by delegate
    val description: String by delegate

    val author: String by delegate

    val url: String by delegate
    val urlSrc: String by delegate
    val urlIssues: String by delegate

    val platform: String by delegate
    val entryPoint: String by delegate
    val rootPackage: String by delegate

    companion object {

        /**
         * Creates plugin properties from the project.
         *
         * @param project The project.
         * @return The plugin properties.
         */
        fun from(project: Project): PluginProps {
            val props = project.properties
                .filter { prop -> prop.key.startsWith('$') }
                .map { prop -> prop.key.drop(1) to prop.value as String }
                .toMap(mutableMapOf())

            val regradle = project.regradle()
            val module = regradle?.module ?: ModuleType.None

            // Base properties
            val name = props.getOrPut("name") { "ReTemplate" }

            val group = props.getOrPut("group") { "dev.remodded" }
            var platform = project.name.uppercaseFirstChar()
            var platformPackage = platform.lowercase().replace('-', '_')

            if (module != ModuleType.None && regradle != null) {
                platform = module.name.lowercase().uppercaseFirstChar()
                platformPackage = module.platformPackageName(regradle.mcVersion)
            }

            val rootPackage = "$group.${name.lowercase()}.$platformPackage"
            val mainClassname = "$name$platform"


            props.getOrPut("id") { name.lowercase() }
            props.getOrPut("version") { "0.0.1-SNAPSHOT" }
            props.getOrPut("description") { "A Minecraft plugin." }

            // Authors
            props.getOrPut("author") { "ReModded Team" }

            // URLs
            val url = props.getOrPut("url") { "https://example.com/" }
            props.getOrPut("url_src") { url }
            props.getOrPut("url_issues") { url }

            // Class paths
            props["root_package"] = rootPackage
            props["platform"] = platform
            props["main_class"] = mainClassname
            props["entry_point"] = "$rootPackage.$mainClassname"

            return PluginProps(props)
        }
    }
}

/**
 * Gets the plugin properties from the project.
 *
 * @return The plugin properties.
 */
fun Project.getPluginProps() = PluginProps.from(this)
