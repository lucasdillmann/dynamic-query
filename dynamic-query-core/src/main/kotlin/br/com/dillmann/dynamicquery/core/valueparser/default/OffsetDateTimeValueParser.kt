package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser
import java.time.OffsetDateTime

/**
 * Default implementation of a [ValueParser] for [OffsetDateTime]
 */
internal object OffsetDateTimeValueParser: DefaultValueParser<OffsetDateTime>(OffsetDateTime::class) {

    /**
     * Parses the given [String] value as an instance of [OffsetDateTime]
     *
     * @param value Value to be parsed
     * @param targetType Resulting type of the needed conversion
     */
    override fun parse(value: String, targetType: Class<*>): OffsetDateTime =
        OffsetDateTime.parse(value)
}
