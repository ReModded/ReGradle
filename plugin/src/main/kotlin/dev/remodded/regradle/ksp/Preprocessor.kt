package dev.remodded.regradle.ksp

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import java.io.OutputStream
import java.util.Base64

class Preprocessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>
) : SymbolProcessor {

    private var processed = false

    operator fun OutputStream.plusAssign(str: String) {
        this.write(str.toByteArray())
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (processed) return emptyList()

        processed = true

        val packageName = decodeFromBase64(options["root_package"]!!)

        logger.info("Generating Constants for $packageName")

        codeGenerator.createNewFile(
            dependencies = Dependencies.ALL_FILES,
            packageName = packageName,
            fileName = "Constants"
        ).use { file ->
            file += "package $packageName\n\n"
            file += "object Constants {\n"

            options.forEach {
                file += "    const val ${it.key.uppercase()} = \"${decodeFromBase64(it.value)}\"\n"
            }

            file += "}\n"
        }

        return emptyList()
    }

    private fun decodeFromBase64(value: String): String {
        val decoder = Base64.getDecoder()
        return decoder.decode(value).toString(Charsets.UTF_8)
    }
}
