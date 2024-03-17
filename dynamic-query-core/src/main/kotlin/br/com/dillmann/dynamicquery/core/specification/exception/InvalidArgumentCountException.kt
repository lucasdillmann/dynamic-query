package br.com.dillmann.dynamicquery.core.specification.exception

import br.com.dillmann.dynamicquery.core.specification.predicate.PredicateType

/**
 * [SpecificationException] specialization for the error scenarios where an invalid amount of arguments
 * where provided for an operation
 *
 * @property attributeName Path of the attribute
 * @property predicateType Type of the predicate
 * @property currentArgumentCount Count of provided arguments
 * @property minimumArgumentCount Expected minimum amount of arguments
 * @property maximumArgumentCount Expected maximum amount of arguments
 */
class InvalidArgumentCountException(
    val attributeName: String,
    val predicateType: PredicateType,
    val currentArgumentCount: Int,
    val minimumArgumentCount: Int,
    val maximumArgumentCount: Int,
): SpecificationException(
    "Operation [$predicateType] on attribute [$attributeName] expected between [$minimumArgumentCount] and " +
        "[$maximumArgumentCount] arguments and [$currentArgumentCount] where provided"
)
