package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser
import java.time.ZonedDateTime

/**
 * Default implementation of a [ValueParser] for [ZonedDateTime]
 */
internal object ZonedDateTimeValueParser: DefaultValueParser<ZonedDateTime>(ZonedDateTime::class) {

    /**
     * Parses the given [String] value as an instance of [ZonedDateTime]
     *
     * @param value Value to be parsed
     */
    override fun parse(value: String): ZonedDateTime =
        ZonedDateTime.parse(value)
}
