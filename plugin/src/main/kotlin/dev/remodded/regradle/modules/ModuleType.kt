package dev.remodded.regradle.modules

import dev.remodded.regradle.MCVersion

enum class ModuleType(
    private val _dependency: ModuleType? = null,
    val singular: Boolean = true,
) {
    None,
    ROOT,

    API,
    COMMON(API),

    PROXY(COMMON),
    SERVER(COMMON),

    PAPER(SERVER),
    SPONGE(SERVER, false) {
        override fun platformPackageName(version: MCVersion): String {
            return "sponge_api${version.spongeAPIVersion.major}"
        }
    },
//    FABRIC,

    VELOCITY(PROXY),
//    WATERFALL,
    ;

    val dependency get() = _dependency ?: None

    open fun platformPackageName(version: MCVersion): String {
        return name.lowercase().replace('-', '_')
    }
}
