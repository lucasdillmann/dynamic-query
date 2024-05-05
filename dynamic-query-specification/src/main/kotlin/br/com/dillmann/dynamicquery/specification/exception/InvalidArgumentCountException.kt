package br.com.dillmann.dynamicquery.specification.exception

/**
 * [SpecificationException] specialization for the error scenarios where an invalid amount of arguments
 * where provided for an operation
 *
 * @property operation Type of the predicate or operation
 * @property currentArgumentCount Count of provided arguments
 * @property minimumArgumentCount Expected minimum number of arguments
 * @property maximumArgumentCount Expected maximum number of arguments
 */
class InvalidArgumentCountException(
    val operation: String,
    val currentArgumentCount: Int,
    val minimumArgumentCount: Int,
    val maximumArgumentCount: Int,
): SpecificationException(
    "Operation [$operation] expects between [$minimumArgumentCount] and [$maximumArgumentCount] arguments and " +
        "[$currentArgumentCount] where provided"
)
