package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser
import java.math.BigDecimal

/**
 * Default implementation of a [ValueParser] for [BigDecimal]
 */
internal object BigDecimalValueParser: DefaultValueParser<BigDecimal>(BigDecimal::class) {

    /**
     * Parses the given [String] value as an instance of [BigDecimal]
     *
     * @param value Value to be parsed
     */
    override fun parse(value: String): BigDecimal =
        value.toBigDecimal()
}
