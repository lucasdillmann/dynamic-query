package br.com.dillmann.dynamicquery.specification

import br.com.dillmann.dynamicquery.specification.exception.InvalidArgumentCountException
import br.com.dillmann.dynamicquery.specification.group.AndGroupSpecification
import br.com.dillmann.dynamicquery.specification.group.LogicalOperatorType
import br.com.dillmann.dynamicquery.specification.group.OrGroupSpecification
import br.com.dillmann.dynamicquery.specification.predicate.PredicateSpecification
import br.com.dillmann.dynamicquery.specification.predicate.PredicateType
import br.com.dillmann.dynamicquery.specification.predicate.PredicateType.*
import br.com.dillmann.dynamicquery.specification.predicate.binary.*
import br.com.dillmann.dynamicquery.specification.predicate.collection.InCollectionSpecification
import br.com.dillmann.dynamicquery.specification.predicate.collection.NotInCollectionSpecification
import br.com.dillmann.dynamicquery.specification.predicate.negation.NegationSpecification
import br.com.dillmann.dynamicquery.specification.predicate.range.BetweenRangeSpecification
import br.com.dillmann.dynamicquery.specification.predicate.range.NotBetweenRangeSpecification
import br.com.dillmann.dynamicquery.specification.predicate.unary.IsEmptyUnarySpecification
import br.com.dillmann.dynamicquery.specification.predicate.unary.IsNotEmptyUnarySpecification
import br.com.dillmann.dynamicquery.specification.predicate.unary.IsNotNullUnarySpecification
import br.com.dillmann.dynamicquery.specification.predicate.unary.IsNullUnarySpecification

/**
 * [DynamicQuerySpecification] factory
 */
object DynamicQuerySpecificationFactory {

    /**
     * Builds and returns a [PredicateSpecification] instance for a filter operation
     *
     * @param type Type of the filter
     * @param attributeName Name of the attribute on where the operation will be performed
     * @param arguments List of input arguments for the operation. Size can vary depending on the type of the filter.
     * Defaults to an empty list.
     */
    @JvmStatic
    @JvmOverloads
    fun predicate(
        type: PredicateType,
        attributeName: String,
        arguments: List<String> = emptyList(),
    ): DynamicQuerySpecification {
        checkArgumentCount(type, arguments, attributeName)

        return when (type) {
            // Unary
            IS_NULL -> IsNullUnarySpecification(attributeName)
            IS_NOT_NULL -> IsNotNullUnarySpecification(attributeName)
            IS_EMPTY -> IsEmptyUnarySpecification(attributeName)
            IS_NOT_EMPTY -> IsNotEmptyUnarySpecification(attributeName)

            // Range
            BETWEEN -> BetweenRangeSpecification(attributeName, arguments.first(), arguments.last())
            NOT_BETWEEN -> NotBetweenRangeSpecification(attributeName, arguments.first(), arguments.last())

            // Collection
            IN -> InCollectionSpecification(attributeName, arguments)
            NOT_IN -> NotInCollectionSpecification(attributeName, arguments)

            // Binary
            EQUALS -> EqualsBinarySpecification(attributeName, arguments.first())
            EQUALS_IGNORE_CASE -> EqualsCaseInsensitiveBinarySpecification(attributeName, arguments.first())
            NOT_EQUALS -> NotEqualsBinarySpecification(attributeName, arguments.first())
            NOT_EQUALS_IGNORE_CASE -> NotEqualsCaseInsensitiveBinarySpecification(attributeName, arguments.first())
            GREATER_THAN -> GreaterThanBinarySpecification(attributeName, arguments.first())
            GREATER_THAN_OR_EQUALS -> GreaterThanOrEqualsBinarySpecification(attributeName, arguments.first())
            LESS_THAN -> LessThanBinarySpecification(attributeName, arguments.first())
            LESS_THAN_OR_EQUALS -> LessThanOrEqualsBinarySpecification(attributeName, arguments.first())
            LIKE -> LikeBinarySpecification(attributeName, arguments.first())
            LIKE_IGNORE_CASE -> LikeCaseInsensitiveBinarySpecification(attributeName, arguments.first())
            NOT_LIKE -> NotLikeBinarySpecification(attributeName, arguments.first())
            NOT_LIKE_IGNORE_CASE -> NotLikeCaseInsensitiveBinarySpecification(attributeName, arguments.first())
        }
    }

    private fun checkArgumentCount(type: PredicateType, arguments: List<String>, attributeName: String) {
        val argumentCountRange = type.argumentCountRange
        if (arguments.size !in argumentCountRange)
            throw InvalidArgumentCountException(
                attributeName,
                type,
                arguments.size,
                argumentCountRange.first,
                argumentCountRange.last,
            )
    }

    /**
     * Builds and returns a [DynamicQuerySpecification] instance for a negation/inversion expression
     *
     * @param specification Expression to be negated
     */
    @JvmStatic
    fun negate(specification: DynamicQuerySpecification): DynamicQuerySpecification =
        NegationSpecification(specification)

    /**
     * Builds and returns a [DynamicQuerySpecification] instance for a group (logic expression) operation
     *
     * @param type Type of the logic operator
     * @param leftExpression Left side expression of the operator
     * @param rightExpression Right side expression of the operator
     */
    @JvmStatic
    fun group(
        type: LogicalOperatorType,
        leftExpression: DynamicQuerySpecification,
        rightExpression: DynamicQuerySpecification
    ): DynamicQuerySpecification =
        when (type) {
            LogicalOperatorType.AND -> AndGroupSpecification(leftExpression, rightExpression)
            LogicalOperatorType.OR -> OrGroupSpecification(leftExpression, rightExpression)
        }
}
