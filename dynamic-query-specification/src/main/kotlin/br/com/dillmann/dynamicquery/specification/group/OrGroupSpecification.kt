package br.com.dillmann.dynamicquery.specification.group

import br.com.dillmann.dynamicquery.specification.DynamicQuerySpecification
import jakarta.persistence.criteria.CriteriaBuilder

/**
 * [GroupSpecification] implementation for the OR operator
 *
 * @param leftExpression Left side expression of the operator
 * @param rightExpression Right side expression of the operator
 */
internal class OrGroupSpecification(
    leftExpression: DynamicQuerySpecification,
    rightExpression: DynamicQuerySpecification,
): GroupSpecification(
    leftExpression,
    rightExpression,
    CriteriaBuilder::or,
)
