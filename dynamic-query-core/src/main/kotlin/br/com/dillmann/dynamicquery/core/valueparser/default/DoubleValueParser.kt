package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser

/**
 * Default implementation of a [ValueParser] for [Double]
 */
internal object DoubleValueParser: DefaultValueParser<Double>(Double::class) {

    /**
     * Parses the given [String] value as an instance of [Double]
     *
     * @param value Value to be parsed
     */
    override fun parse(value: String): Double =
        value.toDouble()
}
