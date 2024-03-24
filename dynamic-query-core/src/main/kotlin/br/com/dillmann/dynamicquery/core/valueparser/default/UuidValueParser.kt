package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser
import java.util.*

/**
 * Default implementation of a [ValueParser] for [UUID]
 */
internal object UuidValueParser: DefaultValueParser<UUID>(UUID::class) {

    /**
     * Parses the given [String] value as an instance of [UUID]
     *
     * @param value Value to be parsed
     * @param targetType Resulting type of the needed conversion
     */
    override fun parse(value: String, targetType: Class<*>): UUID =
        UUID.fromString(value)
}
