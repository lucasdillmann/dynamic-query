package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser

/**
 * Default implementation of a [ValueParser] for [Byte]
 */
internal object ByteValueParser: DefaultValueParser<Byte>(Byte::class) {

    /**
     * Parses the given [String] value as an instance of [Byte]
     *
     * @param value Value to be parsed
     */
    override fun parse(value: String): Byte =
        value.toByte()
}
