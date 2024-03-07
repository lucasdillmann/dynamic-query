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
     */
    override fun parse(value: String): LocalDate =
        LocalDate.parse(value)
}
