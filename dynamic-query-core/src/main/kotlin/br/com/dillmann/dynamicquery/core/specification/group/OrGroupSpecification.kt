package br.com.dillmann.dynamicquery.core.specification.group

import br.com.dillmann.dynamicquery.core.specification.DynamicQuerySpecification
import jakarta.persistence.criteria.CriteriaBuilder

/**
 * [GroupSpecification] implementation for the OR operator
 *
 * @param leftExpression Left side expression of the operator
 * @param rightExpression Right side expression of the operator
 */
class OrGroupSpecification(leftExpression: DynamicQuerySpecification, rightExpression: DynamicQuerySpecification):
    GroupSpecification(leftExpression, rightExpression, CriteriaBuilder::or)
