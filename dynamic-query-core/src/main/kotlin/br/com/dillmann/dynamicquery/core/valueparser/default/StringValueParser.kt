package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser

/**
 * Default implementation of a [ValueParser] for [String]
 */
internal object StringValueParser: DefaultValueParser<String>(String::class) {

    /**
     * Parses the given [String] value as an instance of [String]
     *
     * @param value Value to be parsed
     */
    override fun parse(value: String): String =
        value
}
