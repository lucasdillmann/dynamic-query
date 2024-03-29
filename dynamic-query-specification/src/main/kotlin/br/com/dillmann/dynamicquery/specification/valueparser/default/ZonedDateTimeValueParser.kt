package br.com.dillmann.dynamicquery.specification.valueparser.default

import br.com.dillmann.dynamicquery.specification.valueparser.ValueParser
import java.time.ZonedDateTime

/**
 * Default implementation of a [ValueParser] for [ZonedDateTime]
 */
internal object ZonedDateTimeValueParser: DefaultValueParser<ZonedDateTime>(ZonedDateTime::class) {

    /**
     * Parses the given [String] value as an instance of [ZonedDateTime]
     *
     * @param value Value to be parsed
     * @param targetType Resulting type of the needed conversion
     */
    override fun parse(value: String, targetType: Class<*>): ZonedDateTime =
        ZonedDateTime.parse(value)
}
