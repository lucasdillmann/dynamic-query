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
     */
    override fun parse(value: String): OffsetDateTime =
        OffsetDateTime.parse(value)
}
