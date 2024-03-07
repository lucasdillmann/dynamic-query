package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser

/**
 * Default implementation of a [ValueParser] for [Boolean]
 */
internal object BooleanValueParser: DefaultValueParser<Boolean>(Boolean::class) {

    /**
     * Parses the given [String] value as an instance of [Boolean]
     *
     * @param value Value to be parsed
     */
    override fun parse(value: String): Boolean =
        value.toBoolean()
}
