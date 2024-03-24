package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser
import java.math.BigInteger

/**
 * Default implementation of a [ValueParser] for [BigInteger]
 */
internal object BigIntegerValueParser: DefaultValueParser<BigInteger>(BigInteger::class) {

    /**
     * Parses the given [String] value as an instance of [BigInteger]
     *
     * @param value Value to be parsed
     * @param targetType Resulting type of the needed conversion
     */
    override fun parse(value: String, targetType: Class<*>): BigInteger =
        value.toBigInteger()
}
