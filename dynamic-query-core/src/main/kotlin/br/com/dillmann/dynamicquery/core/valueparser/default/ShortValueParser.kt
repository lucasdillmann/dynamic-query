package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser

/**
 * Default implementation of a [ValueParser] for [Short]
 */
internal object ShortValueParser: DefaultValueParser<Short>(Short::class) {

    /**
     * Parses the given [String] value as an instance of [Short]
     *
     * @param value Value to be parsed
     */
    override fun parse(value: String): Short =
        value.toShort()
}
