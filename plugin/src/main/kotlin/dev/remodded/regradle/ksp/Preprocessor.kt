package dev.remodded.regradle.ksp

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import dev.remodded.regradle.utils.Base64Utils
import java.io.OutputStream

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

        val packageName = Base64Utils.decode(options["root_package"]!!)

        logger.info("Generating Constants for $packageName")

        codeGenerator.createNewFile(
            dependencies = Dependencies.ALL_FILES,
            packageName = packageName,
            fileName = "Constants"
        ).use { file ->
            file += "package $packageName\n\n"
            file += "object Constants {\n"

            options.forEach {
                val key = it.key.uppercase()
                val value = Base64Utils.decode(it.value)
                file += "    const val $key = \"$value\"\n"
            }

            file += "}\n"
        }

        return emptyList()
    }
}
