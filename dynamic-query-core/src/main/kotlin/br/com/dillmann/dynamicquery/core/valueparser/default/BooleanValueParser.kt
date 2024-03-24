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
     * @param targetType Resulting type of the needed conversion
     */
    override fun parse(value: String, targetType: Class<*>): Boolean =
        value.toBoolean()
}
