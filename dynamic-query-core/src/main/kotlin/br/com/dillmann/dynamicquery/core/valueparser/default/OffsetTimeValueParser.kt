package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser
import java.time.OffsetTime

/**
 * Default implementation of a [ValueParser] for [OffsetTime]
 */
internal object OffsetTimeValueParser: DefaultValueParser<OffsetTime>(OffsetTime::class) {

    /**
     * Parses the given [String] value as an instance of [OffsetTime]
     *
     * @param value Value to be parsed
     */
    override fun parse(value: String): OffsetTime =
        OffsetTime.parse(value)
}
