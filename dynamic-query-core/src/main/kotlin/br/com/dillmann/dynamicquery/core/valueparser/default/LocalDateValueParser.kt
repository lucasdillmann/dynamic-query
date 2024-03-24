package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser
import java.time.LocalDate

/**
 * Default implementation of a [ValueParser] for [LocalDate]
 */
internal object LocalDateValueParser: DefaultValueParser<LocalDate>(LocalDate::class) {

    /**
     * Parses the given [String] value as an instance of [LocalDate]
     *
     * @param value Value to be parsed
     * @param targetType Resulting type of the needed conversion
     */
    override fun parse(value: String, targetType: Class<*>): LocalDate =
        LocalDate.parse(value)
}
