package br.com.dillmann.dynamicquery.specification

import br.com.dillmann.dynamicquery.specification.group.AndGroupSpecification
import br.com.dillmann.dynamicquery.specification.group.LogicalOperatorType
import br.com.dillmann.dynamicquery.specification.group.OrGroupSpecification
import br.com.dillmann.dynamicquery.specification.parameter.Parameter
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
import br.com.dillmann.dynamicquery.specification.validation.ArgumentCountChecker

/**
 * [DynamicQuerySpecification] factory
 */
object DynamicQuerySpecificationFactory {

    /**
     * Builds and returns a [PredicateSpecification] instance for a filter operation
     *
     * @param type Type of the filter
     * @param arguments List of input arguments for the operation, including the target of the operation (such as the
     * target attribute reference). Size can vary depending on the type of the filter.
     */
    @JvmStatic
    fun predicate(
        type: PredicateType,
        arguments: List<Parameter>,
    ): DynamicQuerySpecification {
        ArgumentCountChecker.check(type.identifier, type.argumentCountRange, arguments.size)

        val target = arguments.first()
        val remainingArguments = arguments.subList(1, arguments.size)

        return when (type) {
            // Unary
            IS_NULL -> IsNullUnarySpecification(target)
            IS_NOT_NULL -> IsNotNullUnarySpecification(target)
            IS_EMPTY -> IsEmptyUnarySpecification(target)
            IS_NOT_EMPTY -> IsNotEmptyUnarySpecification(target)

            // Range
            BETWEEN -> BetweenRangeSpecification(target, remainingArguments.first(), remainingArguments.last())
            NOT_BETWEEN -> NotBetweenRangeSpecification(target, remainingArguments.first(), remainingArguments.last())

            // Collection
            IN -> InCollectionSpecification(target, remainingArguments)
            NOT_IN -> NotInCollectionSpecification(target, remainingArguments)

            // Binary
            EQUALS -> EqualsBinarySpecification(target, remainingArguments.first())
            EQUALS_IGNORE_CASE -> EqualsCaseInsensitiveBinarySpecification(target, remainingArguments.first())
            NOT_EQUALS -> NotEqualsBinarySpecification(target, remainingArguments.first())
            NOT_EQUALS_IGNORE_CASE -> NotEqualsCaseInsensitiveBinarySpecification(target, remainingArguments.first())
            GREATER_THAN -> GreaterThanBinarySpecification(target, remainingArguments.first())
            GREATER_THAN_OR_EQUALS -> GreaterThanOrEqualsBinarySpecification(target, remainingArguments.first())
            LESS_THAN -> LessThanBinarySpecification(target, remainingArguments.first())
            LESS_THAN_OR_EQUALS -> LessThanOrEqualsBinarySpecification(target, remainingArguments.first())
            LIKE -> LikeBinarySpecification(target, remainingArguments.first())
            LIKE_IGNORE_CASE -> LikeCaseInsensitiveBinarySpecification(target, remainingArguments.first())
            NOT_LIKE -> NotLikeBinarySpecification(target, remainingArguments.first())
            NOT_LIKE_IGNORE_CASE -> NotLikeCaseInsensitiveBinarySpecification(target, remainingArguments.first())
        }
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
