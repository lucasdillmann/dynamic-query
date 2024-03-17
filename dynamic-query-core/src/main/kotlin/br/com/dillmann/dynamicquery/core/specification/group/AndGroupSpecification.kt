package br.com.dillmann.dynamicquery.core.specification.group

import br.com.dillmann.dynamicquery.core.specification.Specification
import jakarta.persistence.criteria.CriteriaBuilder

/**
 * [GroupSpecification] implementation for the AND operator
 *
 * @param leftExpression Left side expression of the operator
 * @param rightExpression Right side expression of the operator
 */
class AndGroupSpecification(leftExpression: Specification, rightExpression: Specification):
    GroupSpecification(leftExpression, rightExpression, CriteriaBuilder::and)
