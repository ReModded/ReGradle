package dev.remodded.regradle.utils

import org.gradle.internal.impldep.kotlinx.serialization.Serializable

@Serializable
data class Version (
    val major: Int,
    val minor: Int,
    val patch: Int,
    val build: String? = null,
) {
    companion object {
        val UNKNOWN = Version(-1, -1, -1)

        fun from(version: String): Version {
            if (version == "UNKNOWN" || version.isEmpty())
                return UNKNOWN

            if (!version.contains('.'))
                throw IllegalArgumentException("Invalid version: $version, missing '.'")

            val parts = version.split('.').toMutableList()
            if (parts.size < 2 || parts.size > 3)
                throw IllegalArgumentException("Invalid version: $version. Expected format: major.minor[.patch]")

            var build: String? = null

            // Strip last part from the version build
            val last = parts.last()
            if (last.contains('-')) {
                val dashIndex = last.indexOf('-')
                build = last.substring(dashIndex + 1)
                parts[parts.size - 1] = last.substring(0, dashIndex)
            }

            val major = parts[0].toInt()
            val minor = parts[1].toInt()
            var patch = 0
            if (parts.size > 2)
                patch = parts[2].toInt()

            return Version(major, minor, patch, build)
        }
    }

    override fun toString(): String {
        return toString(true)
    }

    fun toString(keepPatchZero: Boolean): String {
        if (major < 0 || minor < 0 || patch < 0)
            return "UNKNOWN"

        return if (keepPatchZero)
            "$major.$minor.$patch${build?.let { "-$it" } ?: ""}"
        else
            "$major.$minor${if (patch > 0) ".$patch" else ""}${build?.let { ".$it" } ?: ""}"
    }
}
