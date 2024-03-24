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
     * @param targetType Resulting type of the needed conversion
     */
    override fun parse(value: String, targetType: Class<*>): Int =
        value.toInt()
}
