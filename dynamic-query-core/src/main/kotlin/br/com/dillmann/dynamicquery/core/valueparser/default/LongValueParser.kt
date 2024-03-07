package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser

/**
 * Default implementation of a [ValueParser] for [Long]
 */
internal object LongValueParser: DefaultValueParser<Long>(Long::class) {

    /**
     * Parses the given [String] value as an instance of [Long]
     *
     * @param value Value to be parsed
     */
    override fun parse(value: String): Long =
        value.toLong()
}
