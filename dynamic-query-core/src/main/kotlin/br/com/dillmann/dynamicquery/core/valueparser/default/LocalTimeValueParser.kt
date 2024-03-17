package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser
import java.time.LocalTime

/**
 * Default implementation of a [ValueParser] for [LocalTime]
 */
internal object LocalTimeValueParser: DefaultValueParser<LocalTime>(LocalTime::class) {

    /**
     * Parses the given [String] value as an instance of [LocalTime]
     *
     * @param value Value to be parsed
     */
    override fun parse(value: String): LocalTime =
        LocalTime.parse(value)
}
