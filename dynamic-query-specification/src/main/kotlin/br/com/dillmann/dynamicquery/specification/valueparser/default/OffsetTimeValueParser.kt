package br.com.dillmann.dynamicquery.specification.valueparser.default

import br.com.dillmann.dynamicquery.specification.valueparser.ValueParser
import java.time.OffsetTime

/**
 * Default implementation of a [ValueParser] for [OffsetTime]
 */
internal object OffsetTimeValueParser: DefaultValueParser<OffsetTime>(OffsetTime::class) {

    /**
     * Parses the given [String] value as an instance of [OffsetTime]
     *
     * @param value Value to be parsed
     * @param targetType Resulting type of the needed conversion
     */
    override fun parse(value: String, targetType: Class<*>): OffsetTime =
        OffsetTime.parse(value)
}
