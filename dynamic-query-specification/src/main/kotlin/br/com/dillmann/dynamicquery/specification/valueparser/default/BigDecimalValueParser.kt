package br.com.dillmann.dynamicquery.specification.valueparser.default

import br.com.dillmann.dynamicquery.specification.valueparser.ValueParser
import java.math.BigDecimal

/**
 * Default implementation of a [ValueParser] for [BigDecimal]
 */
internal object BigDecimalValueParser: DefaultValueParser<BigDecimal>(BigDecimal::class) {

    /**
     * Parses the given [String] value as an instance of [BigDecimal]
     *
     * @param value Value to be parsed
     * @param targetType Resulting type of the needed conversion
     */
    override fun parse(value: String, targetType: Class<*>): BigDecimal =
        value.toBigDecimal()
}
