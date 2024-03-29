package br.com.dillmann.dynamicquery.specification.valueparser.exception

/**
 * [ValueParseException] specialization for the scenarios where the parser failed to convert the value with an
 * exception
 *
 * @param value Value requested to be converted
 * @param targetType Target type of the conversion
 * @param cause Exception thrown by the parser
 */
class ParseFailedException(value: String?, targetType: Class<*>, cause: Exception):
    ValueParseException(value, targetType, "Parser failed to convert from [$value] to [${targetType.name}]", cause)
