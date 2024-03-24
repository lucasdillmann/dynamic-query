package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser
import java.time.LocalDateTime

/**
 * Default implementation of a [ValueParser] for [LocalDateTime]
 */
internal object LocalDateTimeValueParser: DefaultValueParser<LocalDateTime>(LocalDateTime::class) {

    /**
     * Parses the given [String] value as an instance of [LocalDateTime]
     *
     * @param value Value to be parsed
     * @param targetType Resulting type of the needed conversion
     */
    override fun parse(value: String, targetType: Class<*>): LocalDateTime =
        LocalDateTime.parse(value)
}
