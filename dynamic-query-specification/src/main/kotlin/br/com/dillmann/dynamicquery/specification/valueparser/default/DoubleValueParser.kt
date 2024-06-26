package br.com.dillmann.dynamicquery.specification.valueparser.default

import br.com.dillmann.dynamicquery.specification.valueparser.ValueParser

/**
 * Default implementation of a [ValueParser] for [Double]
 */
internal object DoubleValueParser: DefaultValueParser<Double>(Double::class) {

    /**
     * Parses the given [String] value as an instance of [Double]
     *
     * @param value Value to be parsed
     * @param targetType Resulting type of the needed conversion
     */
    override fun parse(value: String, targetType: Class<*>): Double =
        value.toDouble()
}
