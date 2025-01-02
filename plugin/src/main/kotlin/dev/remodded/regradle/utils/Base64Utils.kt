package dev.remodded.regradle.utils

import java.util.Base64

/**
 * Utility class for base64 encoding and decoding.
 */
object Base64Utils {

    /**
     * Encodes a string to base64.
     * @param string The string to encode.
     * @return The encoded base64 string.
     */
    fun encode(string: String): String {
        val encoder = Base64.getEncoder()
        return encoder.encodeToString(string.toByteArray())
    }

    /**
     * Decodes a base64 string.
     * @param base64 The base64 string to decode.
     * @return The decoded string.
     */
    fun decode(base64: String): String {
        val decoder = Base64.getDecoder()
        return decoder.decode(base64).toString(Charsets.UTF_8)
    }
}
