package dev.remodded.regradle

import org.gradle.api.Project
import org.gradle.api.plugins.ExtraPropertiesExtension
import java.util.*

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
