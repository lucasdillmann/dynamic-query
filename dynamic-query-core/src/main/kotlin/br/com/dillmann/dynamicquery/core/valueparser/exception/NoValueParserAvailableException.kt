package br.com.dillmann.dynamicquery.core.valueparser.exception

import br.com.dillmann.dynamicquery.core.valueparser.ValueParser

/**
 * [ValueParseException] specialization for the scenarios where no value parser is available to handle the
 * conversion
 *
 * @param value Value requested to be converted
 * @param targetType Target type of the conversion (that no parser exists for)
 */
class NoValueParserAvailableException(value: String?, targetType: Class<*>):
    ValueParseException(
        value,
        targetType,
        "No [${ValueParser::class.qualifiedName}] implementation that supports [${targetType.name}]",
    )
