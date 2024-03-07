package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser

/**
 * Default implementation of a [ValueParser] for [Float]
 */
internal object FloatValueParser: DefaultValueParser<Float>(Float::class) {

    /**
     * Parses the given [String] value as an instance of [Float]
     *
     * @param value Value to be parsed
     */
    override fun parse(value: String): Float =
        value.toFloat()
}
