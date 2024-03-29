package br.com.dillmann.dynamicquery.specification.valueparser.default

import br.com.dillmann.dynamicquery.specification.valueparser.ValueParser

/**
 * Default implementation of a [ValueParser] for [Float]
 */
internal object FloatValueParser: DefaultValueParser<Float>(Float::class) {

    /**
     * Parses the given [String] value as an instance of [Float]
     *
     * @param value Value to be parsed
     * @param targetType Resulting type of the needed conversion
     */
    override fun parse(value: String, targetType: Class<*>): Float =
        value.toFloat()
}
