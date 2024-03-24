package br.com.dillmann.dynamicquery.core.valueparser.default

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser

/**
 * Default implementation of a [ValueParser] for [Enum]
 */
internal object EnumValueParser: DefaultValueParser<Enum<*>>(Enum::class) {

    /**
     * Checks if the parser supports the conversion from [String] to a specific target type
     *
     * @param value Value to be parsed
     * @param targetType Resulting type of the needed conversion
     */
    override fun supports(value: String, targetType: Class<*>) =
        targetType.isEnum

    /**
     * Parses the given [String] value as an instance of [Enum]
     *
     * @param value Value to be parsed
     * @param targetType Resulting type of the conversion
     */
    @Suppress("UNCHECKED_CAST")
    override fun parse(value: String, targetType: Class<*>): Enum<*> =
        (targetType as Class<Enum<*>>).enumConstants.first { it.name == value }
}
