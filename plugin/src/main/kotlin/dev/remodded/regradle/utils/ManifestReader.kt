package dev.remodded.regradle.utils

import java.util.jar.Manifest

class ManifestReader(contextClass: Class<*>) {

    constructor(context: Any) : this(context.javaClass)

    val manifest: Manifest = try {
        Manifest(contextClass.getResourceAsStream("/META-INF/MANIFEST.MF"))
    } catch (e: Exception) {
        println("Error while reading manifest:")
        e.printStackTrace()
        Manifest()
    }

    fun getValue(key: String): String? {
        return try {
            manifest.mainAttributes.getValue(key)
        } catch (e: Exception) {
            println("Error while getting manifest value: $key")
            e.printStackTrace()
            null
        }
    }
}
