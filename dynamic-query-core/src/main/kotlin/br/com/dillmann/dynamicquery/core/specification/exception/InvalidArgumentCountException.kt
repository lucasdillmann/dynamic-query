package br.com.dillmann.dynamicquery.core.specification.exception


/**
 * [SpecificationException] specialization for the error scenarios where an invalid amount of arguments
 * where provided for an operation
 *
 * @param attributeName Path of the attribute
 * @param operation Identifier of the operation
 * @param currentArgumentCount Count of provided arguments
 * @param minimumArgumentCount Expected minimum amount of arguments
 * @param maximumArgumentCount Expected maximum amount of arguments
 */
class InvalidArgumentCountException(
    val attributeName: String,
    val operation: String,
    val currentArgumentCount: Int,
    val minimumArgumentCount: Int,
    val maximumArgumentCount: Int,
): SpecificationException(
    "Operation [$operation] on attribute [$attributeName] expected between [$minimumArgumentCount] and " +
        "[$maximumArgumentCount] arguments and [$currentArgumentCount] where provided"
)
