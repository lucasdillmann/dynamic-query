package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser

/**
 * Default implementation of a [ValueParser] for [Int]
 */
internal object IntValueParser: DefaultValueParser<Int>(Int::class) {

    /**
     * Parses the given [String] value as an instance of [Int]
     *
     * @param value Value to be parsed
     */
    override fun parse(value: String): Int =
        value.toInt()
}
