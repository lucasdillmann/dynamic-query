package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser

/**
 * Default implementation of a [ValueParser] for [CharArray]
 */
internal object CharArrayValueParser: DefaultValueParser<CharArray>(CharArray::class) {

    /**
     * Parses the given [String] value as an instance of [CharArray]
     *
     * @param value Value to be parsed
     */
    override fun parse(value: String): CharArray =
        value.toCharArray()
}
