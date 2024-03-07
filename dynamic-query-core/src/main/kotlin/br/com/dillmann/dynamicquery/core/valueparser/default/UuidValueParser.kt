package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser
import java.util.UUID

/**
 * Default implementation of a [ValueParser] for [UUID]
 */
internal object UuidValueParser: DefaultValueParser<UUID>(UUID::class) {

    /**
     * Parses the given [String] value as an instance of [UUID]
     *
     * @param value Value to be parsed
     */
    override fun parse(value: String): UUID =
        UUID.fromString(value)
}
