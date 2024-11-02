package dev.remodded.regradle.utils

import kotlin.reflect.KProperty

/**
 * A delegate that maps properties to values in a map.
 *
 * @param T The type of the values in the map.
 * @property map The map to use for the property lookup.
 */
class PascalCaseMapDelegate<T>(private val map: Map<String, T>) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val value = map[property.name] ?: map[snakeCase(property.name)]
        return value ?: throw IllegalArgumentException("Property ${property.name} not found in map")
    }

    companion object {
        private val camelCaseRegex = Regex("([a-z])([A-Z]+)")
        private val pascalCaseRegex = Regex("([A-Z])([A-Z][a-z])")

        private fun snakeCase(camelCase: String): String {
            return camelCase
                .replace(camelCaseRegex, "$1_$2")
                .replace(pascalCaseRegex, "$1_$2")
                .lowercase()
        }
    }
}
