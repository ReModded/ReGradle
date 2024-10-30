package dev.remodded.regradle.utils

import org.gradle.api.plugins.ExtraPropertiesExtension

/**
 * Get a property from the project's extra properties that is nullable.
 *
 * @param name The name of the property.
 * @return The property value.
 */
fun <T : Any> ExtraPropertiesExtension.getOptional(name: String): T? {
    @Suppress("UNCHECKED_CAST")
    return if (has(name)) get(name) as? T else null
}
