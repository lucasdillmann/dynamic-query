package br.com.dillmann.dynamicquery.core.specification.exception

import br.com.dillmann.dynamicquery.core.specification.filter.PredicateType


/**
 * [SpecificationException] specialization for the error scenarios where an invalid amount of arguments
 * where provided for an operation
 *
 * @param attributeName Path of the attribute
 * @param predicateType Type of the predicate
 * @param currentArgumentCount Count of provided arguments
 * @param minimumArgumentCount Expected minimum amount of arguments
 * @param maximumArgumentCount Expected maximum amount of arguments
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
