package br.com.dillmann.dynamicquery.core.valueparser.exception

import br.com.dillmann.dynamicquery.core.DynamicQueryException

/**
 * [DynamicQueryException] specialization for the error scenarios of value conversions/parsing
 *
 * @property value Value requested to be converted
 * @property targetType Target type of the conversion
 * @param message Details about what happened
 * @param cause Cause of the error when another exception was raised during the process of conversion
 */
open class ValueParseException(
    val value: String?,
    val targetType: Class<*>,
    message: String,
    cause: Exception? = null
) : DynamicQueryException(message, cause)
