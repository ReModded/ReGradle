package dev.remodded.regradle

import org.gradle.api.Project
import org.gradle.api.plugins.ExtraPropertiesExtension
import org.gradle.kotlin.dsl.support.uppercaseFirstChar
import java.util.*

fun Project.getPluginProps(): Map<String, String> {
    val props = this.properties
        .filter { prop -> prop.key.startsWith('$') }
        .map { prop -> prop.key.drop(1) to prop.value as String }
        .toMap(mutableMapOf())

    val name = props["name"] ?: "ReTemplate"
    val platform = this.name.uppercaseFirstChar()
    val group = props["group"] ?: "dev.remodded"

    val mainClassname =
        "$name${if (platform.contains('-')) platform.substring(0, platform.lastIndexOf('-')) else platform}"

    props["entry_point"] = "$group.${name.lowercase()}.${platform.lowercase().replace('-', '_')}.$mainClassname"

    return props
}

fun <T : Any> ExtraPropertiesExtension.getOptional(name: String): T? {
    @Suppress("UNCHECKED_CAST")
    return if (has(name)) get(name) as? T else null
}

fun Project.getProjectSuffix(): String {
    val name = project.name.replace("api", "API")
    return name.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
}
