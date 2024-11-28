package dev.remodded.regradle

import dev.remodded.regradle.utils.Version

enum class MCVersion(
    val version: Version,
    val spongeAPIVersion: Version,
) {
    UNKNOWN(Version.UNKNOWN, Version.UNKNOWN),

    V1_21(1, 21, 0, "12.0.0-SNAPSHOT"),
    V1_21_1(1, 21, 1, "12.0.0-SNAPSHOT"),
    V1_21_3(1, 21, 3, "13.0.0-SNAPSHOT"),
    ;

    companion object {
        val LATEST = V1_21_3
    }

    constructor(major: Int, minor: Int, patch: Int, spongeAPIVersion: String) : this(Version(major, minor, patch), Version.from(spongeAPIVersion))
    constructor(major: Int, minor: Int, patch: Int, spongeAPIVersion: Version) : this(Version(major, minor, patch), spongeAPIVersion)

    override fun toString(): String {
        return version.toString(false)
    }
}
